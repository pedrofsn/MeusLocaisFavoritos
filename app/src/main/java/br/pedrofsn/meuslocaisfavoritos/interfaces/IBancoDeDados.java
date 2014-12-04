package br.pedrofsn.meuslocaisfavoritos.interfaces;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import br.pedrofsn.meuslocaisfavoritos.model.Local;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public interface IBancoDeDados {

    public void createLocal(Local local);

    public List<Local> readLocal();

    public void deleteLocal(long id);

    public boolean existsLocal(LatLng latLng);

}
