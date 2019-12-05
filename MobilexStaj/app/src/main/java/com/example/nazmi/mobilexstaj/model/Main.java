package com.example.nazmi.mobilexstaj.model;

/**
 * Created by Nazmican GÖKBULUT
 */
public class Main {
    //Todo: Identify Main Object
    private double temp;
    private int pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;

    //Todo: Main to Constructor
    public Main() {
    }

    //Todo: Main for Getter Setter Methods

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
