package br.pedrofsn.meuslocaisfavoritos.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class Local implements Serializable {
    private long id;
    private String endereco;
    private String cidade;
    private String pais;
    private String nome;
    private double latitude;
    private double longitude;
    private Date dataDoCheckin;

    public Local() {

    }

    public Local(String endereco, String cidade, String pais) {
        this.endereco = endereco;
        this.cidade = cidade;
        this.pais = pais;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricaoDoEndereco() {
        String descricao = this.pais + " - " + this.cidade;
        return descricao.trim();
    }


}
