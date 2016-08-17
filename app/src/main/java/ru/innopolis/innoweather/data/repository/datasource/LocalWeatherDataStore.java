package ru.innopolis.innoweather.data.repository.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ru.innopolis.innoweather.data.cache.CityCache;
import ru.innopolis.innoweather.data.cache.WeatherCache;
import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.domain.Weather;
import rx.Observable;

public class LocalWeatherDataStore implements WeatherDataStore {
    private static final String TAG = "LocalCityDataStore";

    private final WeatherCache weatherCache;

    @Inject
    public LocalWeatherDataStore(WeatherCache weatherCache) {
        this.weatherCache = weatherCache;
    }

    public Observable<Boolean> addCity(WeatherEntity weatherEntity) {
        weatherCache.put(weatherEntity);
        return Observable.just(Boolean.TRUE);
    }

    @Override
    public Observable<WeatherEntity> weatherEntityDetails(int cityId, String units) {
        return weatherCache.getWeather(cityId);
    }
}
