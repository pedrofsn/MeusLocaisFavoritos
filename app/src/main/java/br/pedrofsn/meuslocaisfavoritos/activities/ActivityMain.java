package br.pedrofsn.meuslocaisfavoritos.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.fragments.DialogFragmentRota;
import br.pedrofsn.meuslocaisfavoritos.fragments.FragmentInformacoes;
import br.pedrofsn.meuslocaisfavoritos.fragments.FragmentMaps;
import br.pedrofsn.meuslocaisfavoritos.model.Local;
import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;
import br.pedrofsn.meuslocaisfavoritos.tasks.AsyncTaskConsultaDirection;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class ActivityMain extends ActionBarActivity {

    private RelativeLayout relativeLayoutInfoBottom;

    private FragmentMaps fragmentMaps;
    private FragmentInformacoes fragmentInformacoes;

    private Marker markerSelecionado;
    private Local localSelecionado;

    private DirectionResponse directionResponse;

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
            case R.id.exibirRota:
                exibirDialogFragmentRota();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isInfoLocationVisible() {
        return relativeLayoutInfoBottom.getVisibility() == View.VISIBLE ? true : false;
    }

    public void exibirInformacoes(boolean exibir) {
        if (exibir) {
            relativeLayoutInfoBottom.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutInfoBottom.setVisibility(View.GONE);
        }
    }

    public void setDirectionResponse(DirectionResponse directionResponse) {
        if (directionResponse != null) {
            this.directionResponse = directionResponse;
            this.markerSelecionado.setPosition(directionResponse.getLatLngDestino());
        }
    }

    public void desenharRota() {
        if (directionResponse != null) {
            fragmentMaps.desenharRota(directionResponse);
            exibirDialogFragmentRota();
        }
    }

    public Marker getMarkerSelecionado() {
        return markerSelecionado;
    }

    public void setMarkerSelecionado(Marker markerSelecionado) {
        this.markerSelecionado = markerSelecionado;
        relativeLayoutInfoBottom.setVisibility(View.VISIBLE);

        if (localSelecionado == null) {
            localSelecionado = new Local();
        }

        localSelecionado.setLatLng(markerSelecionado.getPosition());

        new AsyncTaskConsultaDirection(fragmentInformacoes, fragmentMaps.getMinhaLocalizacao(), markerSelecionado.getPosition()).execute();
    }

    public void exibirDialogFragmentRota() {
        if (isInfoLocationVisible()) {
            DialogFragmentRota dialogFragmentRota = new DialogFragmentRota();

            Bundle args = new Bundle();
            args.putSerializable("DirectionResponse", directionResponse);
            dialogFragmentRota.setArguments(args);

            dialogFragmentRota.show(getSupportFragmentManager(), "dialogFragmentRota");
        } else {
            Toast.makeText(this, "Selecione um destino para gerar uma rota", Toast.LENGTH_SHORT).show();
        }
    }
}