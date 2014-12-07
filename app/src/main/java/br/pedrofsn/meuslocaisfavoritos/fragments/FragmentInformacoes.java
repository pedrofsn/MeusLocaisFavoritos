package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.dialogs.DialogFragmentCheckin;
import br.pedrofsn.meuslocaisfavoritos.interfaces.IAsyncTaskConsultaDirection;
import br.pedrofsn.meuslocaisfavoritos.interfaces.ICallbackDialogCheckin;
import br.pedrofsn.meuslocaisfavoritos.model.Local;
import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class FragmentInformacoes extends Fragment implements IAsyncTaskConsultaDirection, ICallbackDialogCheckin, View.OnClickListener {

    private LinearLayout linearLayoutBlocoInformacoes;
    private TextView textViewTitulo;
    private TextView textViewDescricao;
    private TextView textViewDistancia;
    private ImageView imageViewCheckin;
    private ImageView imageViewIr;
    private ProgressBar progressBar;

    private Local local = new Local();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informacoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutBlocoInformacoes = (LinearLayout) view.findViewById(R.id.linearLayoutBlocoInformacoes);
        textViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
        textViewDescricao = (TextView) view.findViewById(R.id.textViewDescricao);
        textViewDistancia = (TextView) view.findViewById(R.id.textViewDistancia);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        imageViewCheckin = (ImageView) view.findViewById(R.id.imageViewCheckin);
        imageViewIr = (ImageView) view.findViewById(R.id.imageViewIr);
    }

    @Override
    public void onStart() {
        super.onStart();
        imageViewCheckin.setOnClickListener(this);
        imageViewIr.setOnClickListener(this);

        linearLayoutBlocoInformacoes.setOnClickListener(this);
        exibirInformacoes(false);
    }

    public void exibirInformacoes(final boolean exibir) {
        getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (exibir) {
                            linearLayoutBlocoInformacoes.setVisibility(View.VISIBLE);
                            imageViewCheckin.setVisibility(View.VISIBLE);
                            imageViewIr.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            linearLayoutBlocoInformacoes.setVisibility(View.GONE);
                            imageViewCheckin.setVisibility(View.GONE);
                            imageViewIr.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    @Override
    public void setDirection(DirectionResponse directionResponse) {
        if (directionResponse != null && directionResponse.getStatus().equals("ZERO_RESULTS")) {
            Crouton.makeText(getActivity(), getString(R.string.sem_resultados_para_este_destino), Style.ALERT).show();
            //((ActivityMain) getActivity()).exibirInformacoes(false);
        } else if (directionResponse != null) {
            ((ActivityMain) getActivity()).setDirectionResponse(directionResponse);
            local.setLatLng(directionResponse.getLatLngDestino());
            local.setEndereco(directionResponse.getEnderecoDoDestino());

            textViewTitulo.setText(directionResponse.getLatLngDestino().latitude + ", " + directionResponse.getLatLngDestino().longitude);

            if (directionResponse.getTextDistancia() != null)
                textViewDistancia.setText(getString(R.string.distancia_dois_pontos).concat(directionResponse.getTextDistancia()));

            if (directionResponse.getEnderecoDoDestino() != null) {
                textViewDescricao.setText(local.getEndereco());
            } else {
                textViewDescricao.setText(getString(R.string.carregando_endereco_tres_pontos));
            }

            exibirInformacoes(true);
        }
    }

    @Override
    public void processandoAsyncTaskDirection() {
        exibirInformacoes(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewCheckin:
                chamarDialogCheckin();
                break;

            case R.id.imageViewIr:
                ((ActivityMain) getActivity()).desenharRota();
                break;

            case R.id.linearLayoutBlocoInformacoes:
                chamarDialogCheckin();
                break;
        }
    }

    @Override
    public void salvarEndereco(String nome) {
        local.setNome(nome);
        local.setDataDoCheckin(new Date().getTime());

        if (DAOLocal.getInstancia().createLocal(local)) {
            Crouton.makeText(getActivity(), getString(R.string.localizacao_salva_com_sucesso_exclamacao), Style.CONFIRM).show();
        } else {
            Crouton.makeText(getActivity(), getString(R.string.ops_nao_conseguimos_salvar_sua_localizacao), Style.ALERT).show();
        }

    }

    private void chamarDialogCheckin() {
        DialogFragmentCheckin dialogFragmentCheckin = new DialogFragmentCheckin();
        dialogFragmentCheckin.setTargetFragment(this, 0);
        dialogFragmentCheckin.show(getChildFragmentManager(), DialogFragmentCheckin.TAG);
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
