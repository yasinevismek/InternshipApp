package com.example.nazmi.mobilexstaj.model;

/**
 * Created by Nazmican GÖKBULUT
 */
public class Sys {
    //Todo: Identify Sys Object
    private int type;
    private int id;
    private double message;
    private String country;
    private int sunrise;
    private int sunset;

    //Todo: Sys to Constructor
    public Sys() {
    }

    //Todo: Sys for Getter Setter Methods

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
