package ru.innopolis.innoweather.data.entity.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.data.entity.internals.Main;
import ru.innopolis.innoweather.domain.Weather;

/**
 * Class needed to transform data entities into domain models
 */
@Singleton
public class WeatherEntityDataMapper {
    private static final String TAG = "WeatherEntityDataMapper";

    /**
     * These values should not be extracted to strings.xml because the {@link WeatherEntityDataMapper}
     * may work outside of Android environment
     */
    private static final String[] DIRECTIONS = {"North", "Northeast", "East","Southeast", "South", "Southwest", "West", "Northwest"};

    @Inject
    public WeatherEntityDataMapper() {
    }

    public Weather transform(WeatherEntity weatherEntity) {
        Weather weather = null;
        if (weatherEntity != null) {
            weather = new Weather(weatherEntity.getCityId());
            Main main = weatherEntity.getMain();
            weather.setHumidity(main.getHumidity());
            weather.setPressure(main.getPressure());
            weather.setTemp(main.getTemp());
            weather.setTempMax(main.getTempMax());
            weather.setTempMin(main.getTempMin());
            weather.setCloudiness(weatherEntity.getWeather().get(0).getDescription());
            weather.setWindDirection(transformWindDirection(weatherEntity.getWind().getDeg()));
            weather.setWindSpeed(weatherEntity.getWind().getSpeed());
        } else {
            Log.e(TAG, "transform: problem with downloaded weatherEntity");
        }

        return weather;
    }

    private String transformWindDirection(double deg) {
        // 360 degrees create the full circle. Direction depends on degree
        int sectionNumber = (int)(deg / (360 / DIRECTIONS.length));
        return DIRECTIONS[sectionNumber];
    }

    public List<Weather> transform(Collection<WeatherEntity> WeatherEntityCollection) {
        List<Weather> weatherList = new ArrayList<>();
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
