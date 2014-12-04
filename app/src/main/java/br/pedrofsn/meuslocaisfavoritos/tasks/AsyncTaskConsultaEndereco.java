package br.pedrofsn.meuslocaisfavoritos.tasks;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.pedrofsn.meuslocaisfavoritos.interfaces.IAsyncTaskConsultaEndereco;
import br.pedrofsn.meuslocaisfavoritos.model.Local;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class AsyncTaskConsultaEndereco extends AsyncTask<Void, Void, Local> {

    private Context context;
    private IAsyncTaskConsultaEndereco callback;
    private Geocoder geocoder = null;
    private List<Address> listaEnderecos = null;
    private MarkerOptions markerSelecionado;

    public AsyncTaskConsultaEndereco(Context context, IAsyncTaskConsultaEndereco callback, Marker markerSelecionado) {
        this.context = context;
        this.callback = callback;
        // O MARKER SÓ PODE SER MANIPULADO NA MAINTHREAD, POR ISTO ESTOU INSTANCIANDO UM MARKEROPTIONS
        this.markerSelecionado = new MarkerOptions().position(new LatLng(markerSelecionado.getPosition().latitude, markerSelecionado.getPosition().longitude));
    }

    @Override
    protected Local doInBackground(Void... voids) {
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            listaEnderecos = geocoder.getFromLocation(markerSelecionado.getPosition().latitude, markerSelecionado.getPosition().longitude, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String endereco = "";
        String cidade = "";
        String pais = "";

        try {
            endereco = listaEnderecos.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cidade = listaEnderecos.get(0).getAddressLine(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            pais = listaEnderecos.get(0).getCountryName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Local(endereco, cidade, pais);
    }

    @Override
    protected void onPostExecute(Local local) {
        super.onPostExecute(local);
        callback.getEnderecoCarregado(local);
    }
}
