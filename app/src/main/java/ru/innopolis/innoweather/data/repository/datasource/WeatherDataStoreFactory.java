package ru.innopolis.innoweather.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.BaseCache;
import ru.innopolis.innoweather.data.cache.Cache;
import ru.innopolis.innoweather.data.cache.FileManager;
import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.data.net.NetworkChecker;
import ru.innopolis.innoweather.data.net.RestApiImpl;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;

@Singleton
public class WeatherDataStoreFactory {
    private static final String TAG = "WeatherDataStoreFactory";

    private final Context context;
    private final Cache<WeatherEntity> weatherCache;
    private final String prefix = "weather_";

    @Inject
    public WeatherDataStoreFactory(Context context, JsonSerializer serializer, FileManager fileManager, ThreadExecutor threadExecutor) {
        this.context = context;
        this.weatherCache = new BaseCache<WeatherEntity>(context, serializer, fileManager, threadExecutor, WeatherEntity.class, prefix);
    }

    public WeatherDataStore create() {
        WeatherDataStore weatherDataStore;
        weatherDataStore = NetworkChecker.isNetworkActive(context) ? createCloudDataStore() : createLocalDataStore();
        return weatherDataStore;
    }

    private WeatherDataStore createCloudDataStore() {
        return new CloudWeatherDataStore(new RestApiImpl(), weatherCache);
    }

    private WeatherDataStore createLocalDataStore() {
        return new LocalWeatherDataStore(weatherCache);
    }


}
