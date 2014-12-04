package br.pedrofsn.meuslocaisfavoritos.uis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import pedrofsn.meus.locais.favoritos.R;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class AdapterCustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context context;
    private DAOLocal daoLocal;

    public AdapterCustomInfoWindow(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mWindow = inflater.inflate(R.layout.marker_info_window, null);
        daoLocal = new DAOLocal(context);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(final Marker marker, View view) {

        String nome = marker.getTitle();
        String latitudeLongitude = "(" + marker.getPosition().latitude + ", " + marker.getPosition().longitude + ")";

        TextView textViewNome = ((TextView) view.findViewById(R.id.textViewNome));
        TextView textViewLatitudeLongitude = ((TextView) view.findViewById(R.id.textViewLatitudeLongitude));


        if (nome == null || nome.equals("")) {
            nome = "Sem nome";
        }

        textViewNome.setText(nome);

        if (latitudeLongitude != null) {
            textViewLatitudeLongitude.setText(latitudeLongitude);
        } else {
            textViewLatitudeLongitude.setText("");
        }


    }
}
