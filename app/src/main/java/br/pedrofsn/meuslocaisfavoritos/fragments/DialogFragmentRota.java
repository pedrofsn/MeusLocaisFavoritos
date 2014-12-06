package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.model.directions.DirectionResponse;

/**
 * Created by pedrofsn on 06/12/2014.
 */
public class DialogFragmentRota extends DialogFragment {

    private TextView textViewDestino;
    private TextView textViewDistancia;
    private TextView textViewRota;
    private Button buttonOk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rota, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewDestino = (TextView) view.findViewById(R.id.textViewDestino);
        textViewDistancia = (TextView) view.findViewById(R.id.textViewDistancia);
        textViewRota = (TextView) view.findViewById(R.id.textViewRota);
        buttonOk = (Button) view.findViewById(R.id.buttonOk);
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().setTitle("Rota");

        DirectionResponse directionResponse = (DirectionResponse) getArguments().getSerializable("DirectionResponse");

        if (directionResponse != null) {
            textViewRota.setText(Html.fromHtml(directionResponse.getRota()));
            textViewDestino.setText(String.format("Rota para: %s", directionResponse.getEnderecoDoDestino()));
            textViewDistancia.setText(String.format("Distância: %s", directionResponse.getTextDistancia()));
        } else {
            textViewRota.setText("Sem rotas para este destino");
            dismiss();
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
