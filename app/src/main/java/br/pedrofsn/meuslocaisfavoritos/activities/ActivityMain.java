package br.pedrofsn.meuslocaisfavoritos.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.fragments.FragmentInformacoes;
import br.pedrofsn.meuslocaisfavoritos.fragments.FragmentMaps;
import br.pedrofsn.meuslocaisfavoritos.model.Local;
import br.pedrofsn.meuslocaisfavoritos.tasks.AsyncTaskConsultaDistancia;
import br.pedrofsn.meuslocaisfavoritos.tasks.AsyncTaskConsultaEndereco;
import pedrofsn.meus.locais.favoritos.R;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class ActivityMain extends ActionBarActivity {

    private RelativeLayout relativeLayoutInfoBottom;

    private FragmentMaps fragmentMaps;
    private FragmentInformacoes fragmentInformacoes;

    private LatLng minhaLocalizacao;
    private Marker markerSelecionado;

    private Local localSelecionado;

    public Local getLocalSelecionado() {
        return localSelecionado;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayoutInfoBottom = (RelativeLayout) findViewById(R.id.relativeLayoutInfoBottom);

        fragmentMaps = (FragmentMaps) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        fragmentInformacoes = ((FragmentInformacoes) getSupportFragmentManager().findFragmentById(R.id.fragmentInformacoes));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toogleMap:
                Log.e("teste", "Capturou: " + new DAOLocal(this).existsLocal(new LatLng(10.00080287174, 10.0003004819155)));
                Log.e("teste", "Capturou: " + new DAOLocal(this).existsLocal(new LatLng(10, 10)));
                Log.e("teste", "Capturou: " + new DAOLocal(this).existsLocal(new LatLng(-16.6596433665014, -49.2821637913585)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setMarkerSelecionado(Marker markerSelecionado) {
        this.markerSelecionado = markerSelecionado;
        relativeLayoutInfoBottom.setVisibility(View.VISIBLE);

        if (localSelecionado == null) {
            localSelecionado = new Local();
        }

        localSelecionado.setLatitude(markerSelecionado.getPosition().latitude);
        localSelecionado.setLongitude(markerSelecionado.getPosition().longitude);
        realizarConsultas();
    }

    public boolean isInfoLocationVisible() {
        return relativeLayoutInfoBottom.getVisibility() == View.VISIBLE ? true : false;
    }

    public void hideInfoLocation(boolean exibir) {
        if (exibir) {
            relativeLayoutInfoBottom.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutInfoBottom.setVisibility(View.GONE);
        }
    }

    private void realizarConsultas() {
        new AsyncTaskConsultaEndereco(this, fragmentInformacoes, markerSelecionado).execute();
        new AsyncTaskConsultaDistancia(fragmentInformacoes, minhaLocalizacao, markerSelecionado.getPosition()).execute();
    }

    public void setMinhaLocalizacao(LatLng minhaLocalizacao) {
        this.minhaLocalizacao = minhaLocalizacao;
    }

    public Marker getMarkerSelecionado() {
        return markerSelecionado;
    }
}
