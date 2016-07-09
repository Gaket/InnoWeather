package ru.innopolis.innoweather.data.repository.datasource;

import ru.innopolis.innoweather.data.net.RestApi;
import ru.innopolis.innoweather.data.net.RestApiImpl;

public class WeatherDataStoreFactory {
    private static final String TAG = "WeatherDataStoreFactory";

    public WeatherDataStore create(int cityId) {
        WeatherDataStore weatherDataStore;
        weatherDataStore = createCloudDataStore();
        return weatherDataStore;
    }

    public WeatherDataStore createCloudDataStore() {
        RestApi restApi = new RestApiImpl();
        return new CloudWeatherDataStore(restApi);
    }
}
