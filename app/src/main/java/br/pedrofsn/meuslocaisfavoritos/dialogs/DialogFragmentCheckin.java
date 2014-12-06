package br.pedrofsn.meuslocaisfavoritos.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.interfaces.ICallbackDialogCheckin;

/**
 * Created by pedro.sousa on 03/12/2014.
 */
public class DialogFragmentCheckin extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "DialogFragmentCheckin";

    private ICallbackDialogCheckin callback;

    private EditText editTextNomeDoLugar;
    private Button buttonOk;
    private Button buttonCancelar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (ICallbackDialogCheckin) getTargetFragment();
        } catch (Exception e) {
            throw new ClassCastException("É necessário implementar a classe ICallbackDialogCheckin");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_checkin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextNomeDoLugar = (EditText) view.findViewById(R.id.editTextNomeDoLugar);
        buttonOk = (Button) view.findViewById(R.id.buttonOk);
        buttonCancelar = (Button) view.findViewById(R.id.buttonCancelar);

        editTextNomeDoLugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    editTextNomeDoLugar.setError(null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setTitle("Checkin");
        buttonOk.setOnClickListener(this);
        buttonCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOk:

                if (editTextNomeDoLugar.getText().toString().length() < 1) {
                    editTextNomeDoLugar.setError("Insira ao menos um caractere!");
                } else {
                    callback.salvarEndereco(editTextNomeDoLugar.getText().toString());
                    getDialog().dismiss();
                }

                break;
            case R.id.buttonCancelar:
                getDialog().dismiss();
                break;
        }
    }
}
