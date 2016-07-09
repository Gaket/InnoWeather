package ru.innopolis.innoweather.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.Main;
import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.data.entity.WeatherEntity;

/**
 * Class needed to transform data entities into domain models
 */
@Singleton
public class WeatherEntityDataMapper {
    private static final String TAG = "WeatherEntityDataMapper";

    @Inject
    public WeatherEntityDataMapper() {
    }

    public Weather transform(WeatherEntity WeatherEntity) {
        Weather weather = null;
        if (WeatherEntity != null) {
            weather = new Weather(WeatherEntity.getCityId());
            Main main = WeatherEntity.getMain();
            weather.setHumidity(main.getHumidity());
            weather.setPressure(main.getPressure());
            weather.setTemp(main.getTemp());
            weather.setTempMax(main.getTempMax());
            weather.setTempMin(main.getTempMin());
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
