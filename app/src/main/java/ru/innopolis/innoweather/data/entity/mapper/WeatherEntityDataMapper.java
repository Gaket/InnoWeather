package ru.innopolis.innoweather.data.entity.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.internals.Main;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.domain.Weather;

/**
 * Class needed to transform data entities into domain models
 */
@Singleton
public class WeatherEntityDataMapper {
    private static final String TAG = "WeatherEntityDataMapper";

    @Inject
    public WeatherEntityDataMapper() {
    }

    public Weather transform(WeatherEntity weatherEntity) {
        Weather weather = null;
        if (weatherEntity != null) {
            if (weatherEntity.getWind() != null &&
                    weatherEntity.getClouds() != null &&
                    weatherEntity.getWeather() != null) {
                weather = new Weather(weatherEntity.getCityId());
                Main main = weatherEntity.getMain();
                weather.setHumidity(main.getHumidity());
                weather.setPressure(main.getPressure());
                weather.setTemp(main.getTemp());
                weather.setTempMax(main.getTempMax());
                weather.setTempMin(main.getTempMin());
                weather.setCloudiness(weatherEntity.getWeather().get(0).getDescription());
            } else {
                Log.e(TAG, "transform: problem with downloaded weatherEntity");
            }
        }
        return weather;
    }

    public List<Weather> transform(Collection<WeatherEntity> WeatherEntityCollection) {
        List<Weather> weatherList = new ArrayList<>(20);
        Weather Weather;
        for (WeatherEntity WeatherEntity : WeatherEntityCollection) {
            Weather = transform(WeatherEntity);
            if (Weather != null) {
                weatherList.add(Weather);
            }
        }

        return weatherList;
    }
}
