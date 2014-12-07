package br.pedrofsn.meuslocaisfavoritos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.adapter.AdapterLocal;
import br.pedrofsn.meuslocaisfavoritos.dao.DataBaseHelper;

/**
 * Created by pedrofsn on 06/12/2014.
 */
public class ActivityLocaisFavoritos extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_favoritos);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new AdapterLocal(DataBaseHelper.getInstancia().readLocal());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("atualizarMapa", true);
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
