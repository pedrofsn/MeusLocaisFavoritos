package br.pedrofsn.meuslocaisfavoritos.model.directions;

public class Distance {
    private String text;
    private Number value;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Number getValue() {
        return this.value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
