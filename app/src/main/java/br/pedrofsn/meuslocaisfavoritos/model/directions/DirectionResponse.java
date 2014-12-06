package br.pedrofsn.meuslocaisfavoritos.model.directions;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class DirectionResponse implements Serializable {
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
        try {
            if (this != null) {
                if (this.getRoutes() != null) {
                    if (this.getRoutes().size() > 0) {
                        if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                            return this.getRoutes().get(0).getLegs().get(0).getDistance() != null ? this.getRoutes().get(0).getLegs().get(0).getDistance().getText() : null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getEnderecoDoDestino() {
        try {
            if (this != null) {
                if (this.getRoutes() != null) {
                    if (this.getRoutes().size() > 0) {
                        if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                            return this.getRoutes().get(0).getLegs().get(0).getEnd_address();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public LatLng getLatLngDestino() {
        try {
            if (this != null) {
                if (this.getRoutes() != null) {
                    if (this.getRoutes().size() > 0) {
                        if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                            return new LatLng(this.getRoutes().get(0).getLegs().get(0).getEnd_location().getLat(), this.getRoutes().get(0).getLegs().get(0).getEnd_location().getLng());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getRota() {
        String rota = "";

        try {
            if (this != null) {
                if (this.getRoutes() != null) {
                    if (this.getRoutes().size() > 0) {
                        if (this.getRoutes().get(0).getLegs() != null && this.getRoutes().get(0).getLegs().size() > 0) {
                            if (this.getRoutes().get(0).getLegs().get(0).getSteps() != null) {
                                for (Steps step : this.getRoutes().get(0).getLegs().get(0).getSteps()) {
                                    rota = rota.concat("<p>" + step.getHtml_instructions().trim() + " </p>");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rota;
    }
}
