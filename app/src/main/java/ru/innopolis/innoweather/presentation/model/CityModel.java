package ru.innopolis.innoweather.presentation.model;

/**
 * City representation
 */
public class CityModel {
    private static final String TAG = "CityModel";

    private int id;
    private String name;
    private String country;
    private Double temp;

    public CityModel(int Id) {
        this.id = Id;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
