package com.example.nazmi.mobilexstaj.model;

/**
 * Created by Nazmican GÖKBULUT
 */
public class Weather {
    //Todo: Identify Weather Object
    private int id;
    private String main;
    private String description;
    private String icon;

    //Todo: Weather to Constructor
    public Weather() {
    }

    //Todo: Weather for Getter Setter Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
