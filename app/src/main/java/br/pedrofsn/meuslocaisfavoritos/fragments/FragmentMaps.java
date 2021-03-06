package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.controller.LocationController;
import br.pedrofsn.meuslocaisfavoritos.dao.DataBaseHelper;
import br.pedrofsn.meuslocaisfavoritos.model.Local;
import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;
import br.pedrofsn.meuslocaisfavoritos.model.directions.Steps;
import br.pedrofsn.meuslocaisfavoritos.uis.AdapterCustomInfoWindow;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class FragmentMaps extends Fragment implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap map;
    private Spinner spinnerMapMode;

    private LocationManager locationManager;
    private Polyline polyline;
    private LatLng loc;

    private DirectionResponse ultimoDirectionResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerMapMode = (Spinner) view.findViewById(R.id.spinnerMapMode);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onStart() {
        super.onStart();
        spinnerMapMode.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.tipos_de_mapa)));
        spinnerMapMode.setOnItemSelectedListener(this);
    }

    private void setUpMapIfNeeded() {
        if (locationManager == null) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        if (map == null) {
            // Tentando obter o mapa do ChildFragmentManager.
            map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Verificando se foi possível obter o mapa
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        carregarLocaisNoMapa();

        Location lastKnownLocation = new LocationController().getLastKnownLocation(locationManager);
        if (lastKnownLocation != null) {
            loc = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
        }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);

        map.setInfoWindowAdapter(new AdapterCustomInfoWindow(getActivity()));

        // Listeners
        map.setOnMapLongClickListener(this);
        map.setOnMapClickListener(this);
        map.setOnInfoWindowClickListener(null);
        map.setOnMarkerClickListener(this);
    }

    private void carregarLocaisNoMapa() {
        for (Local l : DataBaseHelper.getInstancia().readLocal()) {
            map.addMarker(new MarkerOptions().position(l.getLatLng()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (map != null) {
            switch (i) {
                case 1:
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case 2:
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case 3:
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                default:
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        removerUltimoMarkerAdicionado();

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        Marker marker = addMarkerToMap(markerOptions);
        ((ActivityMain) getActivity()).setMarkerSelecionado(marker);

    }

    public Marker addMarkerToMap(MarkerOptions markerOptions) {
        return map.addMarker(markerOptions);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        removerUltimoMarkerAdicionado();
    }

    private void removerUltimoMarkerAdicionado() {
        if (((ActivityMain) getActivity()).isVisibleRelativeLayoutFragmentInformacoes()) {
            ((ActivityMain) getActivity()).exibirInformacoes(false);
        }

        if (((ActivityMain) getActivity()).getMarkerSelecionado() != null && !DataBaseHelper.getInstancia().existsLocal(((ActivityMain) getActivity()).getMarkerSelecionado().getPosition())) {
            ((ActivityMain) getActivity()).getMarkerSelecionado().remove();
        }

        if (polyline != null) {
            polyline.remove();
        }

    }

    public void desenharRota(DirectionResponse directionResponse) {
        if (directionResponse != null && ultimoDirectionResponse != directionResponse) {
            if (polyline != null) {
                polyline.remove();
            }

            PolylineOptions rectOptions = new PolylineOptions();

            for (Steps step : directionResponse.getRoutes().get(0).getLegs().get(0).getSteps()) {
                for (LatLng latLng : decodePoly(step.getPolyline().getPoints())) {
                    rectOptions.add(latLng);
                }
            }

            polyline = map.addPolyline(rectOptions);
            polyline.setColor(Color.BLUE);
            map.setMyLocationEnabled(true);

            ultimoDirectionResponse = directionResponse;
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!DataBaseHelper.getInstancia().existsLocal(marker.getPosition())) {
            marker.hideInfoWindow();
            return true;
        } else {
            removerUltimoMarkerAdicionado();
        }

        return false;
    }

    public LatLng getMinhaLocalizacao() {
        return loc;
    }

    public void atualizarMapa() {
        map.clear();
        carregarLocaisNoMapa();
    }
}
