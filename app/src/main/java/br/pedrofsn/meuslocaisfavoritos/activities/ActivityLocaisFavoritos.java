package br.pedrofsn.meuslocaisfavoritos.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.pedrofsn.meuslocaisfavoritos.R;
import br.pedrofsn.meuslocaisfavoritos.adapter.AdapterLocal;
import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;

/**
 * Created by pedrofsn on 06/12/2014.
 */
public class ActivityLocaisFavoritos extends ActionBarActivity {

    private RecyclerView.Adapter adapter;
    private DAOLocal daoLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_favoritos);

        daoLocal = new DAOLocal(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdapterLocal(daoLocal.readLocal());
        recyclerView.setAdapter(adapter);
    }

}
