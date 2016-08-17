package ru.innopolis.innoweather.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.FileManager;
import ru.innopolis.innoweather.data.cache.WeatherCache;
import ru.innopolis.innoweather.data.cache.WeatherCacheImpl;
import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.net.NetworkChecker;
import ru.innopolis.innoweather.data.net.RestApiImpl;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;

@Singleton
public class WeatherDataStoreFactory {
    private static final String TAG = "WeatherDataStoreFactory";

    private final Context context;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;
    private final NetworkChecker networkChecker;
    private final WeatherCache weatherCache;

    @Inject
    public WeatherDataStoreFactory(Context context, JsonSerializer serializer, FileManager fileManager, ThreadExecutor threadExecutor, NetworkChecker networkChecker) {
        this.context = context;
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
        this.networkChecker = networkChecker;
        this.weatherCache = new WeatherCacheImpl(context, serializer, fileManager, threadExecutor);
    }

    public WeatherDataStore create() {
        WeatherDataStore weatherDataStore;
        weatherDataStore = networkChecker.isNetworkActive() ? createCloudDataStore() : createLocalDataStore();
        return weatherDataStore;
    }

    private WeatherDataStore createCloudDataStore() {
        return new CloudWeatherDataStore(new RestApiImpl(), weatherCache);
    }

    private WeatherDataStore createLocalDataStore() {
        return new LocalWeatherDataStore(weatherCache);
    }


}
