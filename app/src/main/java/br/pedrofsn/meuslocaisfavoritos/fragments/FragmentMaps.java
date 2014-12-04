package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.content.Context;
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

import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.controller.LocationController;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.uis.AdapterCustomInfoWindow;
import pedrofsn.meus.locais.favoritos.R;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class FragmentMaps extends Fragment implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap map;
    private Spinner spinnerMapMode;
    private LocationManager locationManager;
    private DAOLocal daoLocal;

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
        daoLocal = new DAOLocal(getActivity());
        String[] arrayTiposMapa = {"Normal", "Híbrido", "Satélite", "Terreno"};
        spinnerMapMode.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, arrayTiposMapa));
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

        Location lastKnownLocation = new LocationController().getLastKnownLocation(locationManager);
        if (lastKnownLocation != null) {
            LatLng loc = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
            ((ActivityMain) getActivity()).setMinhaLocalizacao(loc);
        }

        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);

        map.setInfoWindowAdapter(new AdapterCustomInfoWindow(getActivity()));

        map.setOnMapLongClickListener(this);
        map.setOnMapClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        removerUltimoMarkerAdicionado(latLng);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        Marker marker = map.addMarker(markerOptions);
        marker.showInfoWindow();
        ((ActivityMain) getActivity()).setMarkerSelecionado(marker);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        removerUltimoMarkerAdicionado(latLng);
    }

    private void removerUltimoMarkerAdicionado(LatLng latLng) {
        if (((ActivityMain) getActivity()).isInfoLocationVisible()) {
            ((ActivityMain) getActivity()).hideInfoLocation(false);
        }

        if (((ActivityMain) getActivity()).getMarkerSelecionado() != null && !daoLocal.existsLocal(latLng)) {
            ((ActivityMain) getActivity()).getMarkerSelecionado().remove();
        }

    }
}
