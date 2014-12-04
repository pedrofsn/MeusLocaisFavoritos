package br.pedrofsn.meuslocaisfavoritos.model.directions;

import java.util.List;

public class DirectionResponse {
    private List<Routes> routes;
    private String status;

    public List<Routes> getRoutes() {
        return this.routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
