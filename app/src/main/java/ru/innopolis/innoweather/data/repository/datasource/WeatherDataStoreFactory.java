package ru.innopolis.innoweather.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.net.RestApiImpl;

@Singleton
public class WeatherDataStoreFactory {
    private static final String TAG = "WeatherDataStoreFactory";

    private final Context context;

    @Inject
    public WeatherDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public WeatherDataStore create() {
        WeatherDataStore weatherDataStore;
        weatherDataStore = createCloudDataStore();
        return weatherDataStore;
    }

    public WeatherDataStore createCloudDataStore() {
        return new CloudWeatherDataStore(new RestApiImpl());
    }
}
