package com.example.nazmi.mobilexstaj.model;

/**
 * Created by Nazmican GÖKBULUT
 */
public class Coord {
    //Todo: Identify Coord Object
    private double lon;
    private double lat;

    //Todo: Coord to Constructor
    public Coord() {
    }

    //Todo: Coord for Getter Setter Methods

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return new StringBuilder('[').append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
