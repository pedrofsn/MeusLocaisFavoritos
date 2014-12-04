package br.pedrofsn.meuslocaisfavoritos.model.directions;

import java.util.List;

public class Routes {
    private Bounds bounds;
    private String copyrights;
    private List<Legs> legs;
    private Overview_polyline overview_polyline;
    private String summary;
    private List warnings;
    private List waypoint_order;

    public Bounds getBounds() {
        return this.bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return this.copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<Legs> getLegs() {
        return this.legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public Overview_polyline getOverview_polyline() {
        return this.overview_polyline;
    }

    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List getWarnings() {
        return this.warnings;
    }

    public void setWarnings(List warnings) {
        this.warnings = warnings;
    }

    public List getWaypoint_order() {
        return this.waypoint_order;
    }

    public void setWaypoint_order(List waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}
