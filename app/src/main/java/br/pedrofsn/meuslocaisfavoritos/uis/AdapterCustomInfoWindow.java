package br.pedrofsn.meuslocaisfavoritos.uis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.model.Local;

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

        TextView textViewNome = ((TextView) view.findViewById(R.id.textViewNome));
        TextView textViewHoras = ((TextView) view.findViewById(R.id.textViewHoras));
        TextView textViewEndereco = ((TextView) view.findViewById(R.id.textViewEndereco));

        Local local = daoLocal.readLocal(marker.getPosition());
        ((ActivityMain) context).setLocal(local);
        ((ActivityMain) context).setMarkerSelecionado(marker);

        if (local != null) {
            textViewNome.setText(local.getNome());
            textViewHoras.setText(local.getDataDoCheckin().toString());
            textViewEndereco.setText(local.getEndereco());

            ((ActivityMain) context).exibirInformacoes(false);
            ((ActivityMain) context).exibirInformacoesDaRota(true);
        }


    }
}
