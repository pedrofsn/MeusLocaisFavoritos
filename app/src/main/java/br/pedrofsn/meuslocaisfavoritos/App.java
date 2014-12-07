package br.pedrofsn.meuslocaisfavoritos;

import android.app.Application;

import br.pedrofsn.meuslocaisfavoritos.dao.DAOLocal;

/**
 * Created by pedrofsn on 07/12/2014.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DAOLocal.instanciarDao(this);
    }
}
