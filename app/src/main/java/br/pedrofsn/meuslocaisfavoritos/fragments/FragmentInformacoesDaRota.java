package br.pedrofsn.meuslocaisfavoritos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.activities.ActivityMain;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by pedrofsn on 06/12/2014.
 */
public class FragmentInformacoesDaRota extends Fragment implements View.OnClickListener {

    private TextView textViewDeletar;
    private TextView textViewGerarRota;
    private TextView textViewInfoRota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informacoes_da_rota, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewDeletar = ((TextView) view.findViewById(R.id.textViewDeletar));
        textViewGerarRota = ((TextView) view.findViewById(R.id.textViewGerarRota));
        textViewInfoRota = ((TextView) view.findViewById(R.id.textViewInfoRota));
    }

    @Override
    public void onStart() {
        super.onStart();
        textViewDeletar.setOnClickListener(this);
        textViewGerarRota.setOnClickListener(this);
        textViewInfoRota.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewDeletar:
                new DAOLocal(getActivity()).deleteLocal(((ActivityMain) getActivity()).getLocal().getId());
                ((ActivityMain) getActivity()).atualizarMapa();
                ((ActivityMain) getActivity()).exibirInformacoesDaRota(false);
                break;

            case R.id.textViewGerarRota:
                if (((ActivityMain) getActivity()).getMarkerSelecionado() != null) {
                    ((ActivityMain) getActivity()).desenharRota();
                } else {
                    Crouton.makeText(getActivity(), getString(R.string.rota_ainda_nao_carregada), Style.INFO).show();
                }
                break;

            case R.id.textViewInfoRota:
                if (((ActivityMain) getActivity()).isVisibleRelativeLayoutFragmentInformacoes()) {
                    ((ActivityMain) getActivity()).exibirDialogFragmentRota();
                } else {
                    Crouton.makeText(getActivity(), getString(R.string.pressione_o_botao_ir_para_carregar_a_rota), Style.INFO).show();
                }

                break;
        }
    }
}
