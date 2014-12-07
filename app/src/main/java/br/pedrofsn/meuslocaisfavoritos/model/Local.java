package br.pedrofsn.meuslocaisfavoritos.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class Local implements Serializable {
    private long id;
    private String endereco;
    private String nome;
    private LatLng latLng;
    private Date dataDoCheckin;

    public Local() {

    }

    public Date getDataDoCheckin() {
        return dataDoCheckin;
    }

    public void setDataDoCheckin(Long milisegundos) {
        Date date = new Date();
        date.setTime(milisegundos);
        this.dataDoCheckin = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latLng.latitude;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public double getLongitude() {
        return latLng.longitude;
    }
}
