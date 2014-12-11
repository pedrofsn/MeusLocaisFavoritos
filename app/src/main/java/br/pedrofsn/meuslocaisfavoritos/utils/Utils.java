package br.pedrofsn.meuslocaisfavoritos.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.pedrofsn.meuslocaisfavoritos.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Pedro on 11/12/2014.
 */
public class Utils {

    public static boolean isConectado(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Crouton.makeText((Activity) context, context.getString(R.string.erro_conexao), Style.ALERT).show();
            return false;
        }
    }

}
