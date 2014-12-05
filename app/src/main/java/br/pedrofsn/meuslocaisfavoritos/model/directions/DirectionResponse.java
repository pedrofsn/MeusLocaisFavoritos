package br.pedrofsn.meuslocaisfavoritos.model.directions;

import com.google.android.gms.maps.model.LatLng;

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

    public String getTextDistancia() {
        if (this != null) {
            if (this.getRoutes() != null) {
                if (this.getRoutes().size() > 0) {
                    if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                        return this.getRoutes().get(0).getLegs().get(0).getDistance() != null ? this.getRoutes().get(0).getLegs().get(0).getDistance().getText() : null;
                    }
                }
            }
        }

        return null;
    }

    public String getEnderecoDoDestino() {
        if (this != null) {
            if (this.getRoutes() != null) {
                if (this.getRoutes().size() > 0) {
                    if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                        return this.getRoutes().get(0).getLegs().get(0).getEnd_address();
                    }
                }
            }
        }

        return null;
    }

    public LatLng getLatLngDestino() {
        if (this != null) {
            if (this.getRoutes() != null) {
                if (this.getRoutes().size() > 0) {
                    if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                        return new LatLng(this.getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(), this.getRoutes().get(0).getLegs().get(0).getEnd_location().getLng());
                    }
                }
            }
        }

        return null;
    }
}
