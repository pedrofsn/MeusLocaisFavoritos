package br.pedrofsn.meuslocaisfavoritos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.pedrofsn.meuslocaisfavoritos.interfaces.IAsyncTaskConsultaDistancia;
import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;
import br.pedrofsn.meuslocaisfavoritos.model.directions.Distance;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class AsyncTaskConsultaDistancia extends AsyncTask<Void, Void, DirectionResponse> {

    private IAsyncTaskConsultaDistancia callback;
    private LatLng pontoDeOrigem;
    private LatLng pontoDeDestino;

    public AsyncTaskConsultaDistancia(IAsyncTaskConsultaDistancia callback, LatLng pontoDeOrigem, LatLng pontoDeDestino) {
        this.callback = callback;
        this.pontoDeOrigem = pontoDeOrigem;
        this.pontoDeDestino = pontoDeDestino;
    }

    @Override
    protected DirectionResponse doInBackground(Void... voids) {
        return getDirectionResponse(pontoDeOrigem, pontoDeDestino);
    }

    @Override
    protected void onPostExecute(DirectionResponse result) {
        super.onPostExecute(result);
        if (result != null)
            callback.setDistancia(result);
    }

    public DirectionResponse getDirectionResponse(LatLng start, LatLng end /*, String mode */) {
        String url = "http://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end.latitude + "," + end.longitude
                + "&sensor=false&units=metric&mode=driving";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();

            return new Gson().fromJson(converterInputStreamParaString(in), DirectionResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String converterInputStreamParaString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String linhaAtual = "";
        String resultado = "";
        while ((linhaAtual = bufferedReader.readLine()) != null)
            resultado += linhaAtual;

        inputStream.close();
        return resultado;
    }
}
