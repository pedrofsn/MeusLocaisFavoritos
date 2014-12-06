package br.pedrofsn.meuslocaisfavoritos.uis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.model.Local;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class AdapterCustomInfoWindow implements GoogleMap.InfoWindowAdapter, View.OnClickListener {

    private final View mWindow;
    private Context context;
    private DAOLocal daoLocal;
    private Map.Entry<Local, Marker> entrySelecionada;

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

        TextView textViewNome = ((TextView) view.findViewById(R.id.textViewNome));
        TextView textViewHoras = ((TextView) view.findViewById(R.id.textViewHoras));
        TextView textViewEndereco = ((TextView) view.findViewById(R.id.textViewEndereco));
        TextView textViewDeletar = ((TextView) view.findViewById(R.id.textViewDeletar));
        TextView textViewIr = ((TextView) view.findViewById(R.id.textViewIr));

        for (Map.Entry<Local, Marker> entry : ((ActivityMain) context).getMapa().entrySet()) {
            if (marker.getPosition().latitude == entry.getValue().getPosition().latitude && marker.getPosition().longitude == entry.getValue().getPosition().longitude) {
                textViewNome.setText(entry.getKey().getId() + " - " + entry.getKey().getNome());
                textViewHoras.setText(entry.getKey().getDataDoCheckin().toString());
                textViewEndereco.setText(entry.getKey().getEndereco());

                ((ActivityMain) context).setVisibilityInfoLocation(false);

                entrySelecionada = entry;

                textViewDeletar.setOnClickListener(this);
                textViewIr.setOnClickListener(this);
                break;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewIr:
                if (entrySelecionada != null)
                    ((ActivityMain) context).desenharRota();
                break;
            case R.id.textViewDeletar:
                if (entrySelecionada != null)
                    daoLocal.deleteLocal(entrySelecionada.getKey().getId());
                break;

        }
    }
}
