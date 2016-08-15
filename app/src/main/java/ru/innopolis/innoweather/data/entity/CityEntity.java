package ru.innopolis.innoweather.data.entity;

import ru.innopolis.innoweather.data.entity.internals.Coordinates;

public class CityEntity {
    private static final String TAG = "CityEntity";

    private int id;
    private String name;
    private String country;
    private Coordinates coordinates;

    public CityEntity() {
    }

    public CityEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

