package br.pedrofsn.meuslocaisfavoritos.model.directions;

/**
 * Created by pedrofsn on 04/12/2014.
 */
public class Polyline {

    private String points;

    public Polyline(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

}
