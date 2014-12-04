package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import br.pedrofsn.meuslocaisfavoritos.interfaces.IAsyncTaskConsultaDistancia;
import br.pedrofsn.meuslocaisfavoritos.interfaces.IAsyncTaskConsultaEndereco;
import br.pedrofsn.meuslocaisfavoritos.model.Local;
import br.pedrofsn.meuslocaisfavoritos.model.directions.Distance;
import pedrofsn.meus.locais.favoritos.R;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class FragmentInformacoes extends Fragment implements IAsyncTaskConsultaEndereco, IAsyncTaskConsultaDistancia, View.OnClickListener {

    private LinearLayout linearLayoutBlocoInformacoes;
    private TextView textViewTitulo;
    private TextView textViewDescricao;
    private TextView textViewDistancia;
    private ImageView imageViewCheckin;
    private ImageView imageViewIr;
    private ProgressBar progressBar;

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
        exibirInformacoes(false);
    }

    private void exibirInformacoes(boolean exibir) {
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

    @Override
    public void setDistancia(Distance distancia) {
        if (distancia != null) {
            textViewDistancia.setText("Distância: ".concat(distancia.getText()));
            exibirInformacoes(true);
        }
    }

    @Override
    public void getEnderecoCarregado(Local local) {
        if (local != null) {

            if (local.getEndereco() != null && !local.getEndereco().equals(""))
                textViewTitulo.setText(local.getEndereco());

            if (!local.getDescricaoDoEndereco().equals("null - null") && !local.getDescricaoDoEndereco().equals("-"))
                textViewDescricao.setText(local.getDescricaoDoEndereco());

            if (((ActivityMain) getActivity()).getLocalSelecionado() != null) {
                ((ActivityMain) getActivity()).getLocalSelecionado().setEndereco(local.getEndereco());
                ((ActivityMain) getActivity()).getLocalSelecionado().setCidade(local.getCidade());
                ((ActivityMain) getActivity()).getLocalSelecionado().setPais(local.getPais());
            }

            exibirInformacoes(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewCheckin:
                Local l = ((ActivityMain) getActivity()).getLocalSelecionado();
                Log.e("teste", "Pegou : " + l.getNome() + " - " + l.getCidade() + " - " + l.getEndereco() + " - " + l.getPais() + " - " + l.getLatitude() + " - " + l.getLongitude());
                new DAOLocal(getActivity()).createLocal(l);

                for (Local local : new DAOLocal(getActivity()).readLocal()) {
                    Log.e("teste", ">> " + local.getLatitude());
                }
                break;

            case R.id.imageViewIr:

                break;
        }
    }
}
