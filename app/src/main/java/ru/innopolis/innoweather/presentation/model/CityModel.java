package ru.innopolis.innoweather.presentation.model;

/**
 * City representation
 */
public class CityModel {
    private static final String TAG = "CityModel";

    private int Id;
    private String Name;
    private String Country;

    public CityModel(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

}
