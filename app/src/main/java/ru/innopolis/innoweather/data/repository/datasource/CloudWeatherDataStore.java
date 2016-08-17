package ru.innopolis.innoweather.data.repository.datasource;

import ru.innopolis.innoweather.data.cache.WeatherCache;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.data.net.RestApi;
import rx.Observable;
import rx.functions.Action1;

public class CloudWeatherDataStore implements WeatherDataStore {
    private static final String TAG = "CloudWeatherDataStore";

    private final RestApi restApi;
    private final WeatherCache weatherCache;

    public CloudWeatherDataStore(RestApi restApi, WeatherCache weatherCache) {
        this.restApi = restApi;
        this.weatherCache = weatherCache;
    }

    @Override
    public Observable<WeatherEntity> weatherEntityDetails(int cityId, String units) {
        return restApi.getCityWeatherByCityId(cityId, units).doOnNext(saveToCacheAction);
    }

    private final Action1<WeatherEntity> saveToCacheAction = weatherEntity -> {
        if (weatherEntity != null) {
            CloudWeatherDataStore.this.weatherCache.put(weatherEntity);
        }
    };
}
