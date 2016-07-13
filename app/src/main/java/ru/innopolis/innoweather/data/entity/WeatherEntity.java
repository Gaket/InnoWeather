package ru.innopolis.innoweather.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.innopolis.innoweather.data.entity.internals.Clouds;
import ru.innopolis.innoweather.data.entity.internals.Main;
import ru.innopolis.innoweather.data.entity.internals.Weather;
import ru.innopolis.innoweather.data.entity.internals.Wind;

public class WeatherEntity {
    private static final String TAG = "WeatherEntity";

    @SerializedName("id")
    private int cityId;
    private Clouds clouds;
    private Main main;
    private List<Weather> weather;
    private Wind wind;


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherEntity{");
        sb.append("\n\t").append("clouds=").append(clouds);
        sb.append(", \n\t").append("main=").append(main);
        sb.append(", \n\t").append("weather=").append(weather);
        sb.append(", \n\t").append("wind=").append(wind);
        sb.append("\n").append('}');
        return sb.toString();
    }
}
