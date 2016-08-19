package ru.innopolis.innoweather.data.repository.datasource;

import javax.inject.Inject;

import ru.innopolis.innoweather.data.cache.Cache;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

public class LocalWeatherDataStore implements WeatherDataStore {
    private static final String TAG = "LocalCityDataStore";

    private final Cache<WeatherEntity> weatherCache;

    @Inject
    public LocalWeatherDataStore(Cache<WeatherEntity> weatherCache) {
        this.weatherCache = weatherCache;
    }

    public Observable<Boolean> addCity(WeatherEntity weatherEntity) {
        weatherCache.put(weatherEntity);
        return Observable.just(Boolean.TRUE);
    }

    @Override
    public Observable<WeatherEntity> weatherEntityDetails(int cityId, String units) {
        return weatherCache.get(cityId);
    }
}
