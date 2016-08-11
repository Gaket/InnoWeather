package ru.innopolis.innoweather.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.mapper.WeatherEntityDataMapper;
import ru.innopolis.innoweather.data.repository.datasource.WeatherDataStore;
import ru.innopolis.innoweather.data.repository.datasource.WeatherDataStoreFactory;
import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;
import rx.Observable;

/**
 * Repository that decides if Cloud or Local datastore should be used
 */
@Singleton
public class WeatherDataRepository implements WeatherRepository {
    private static final String TAG = "WeatherDataRepository";

    private final WeatherDataStoreFactory weatherDataStoreFactory;
    private final WeatherEntityDataMapper weatherEntityDataMapper;

    @Inject
    public WeatherDataRepository(WeatherDataStoreFactory weatherDataStoreFactory, WeatherEntityDataMapper weatherEntityDataMapper) {
        this.weatherDataStoreFactory = weatherDataStoreFactory;
        this.weatherEntityDataMapper = weatherEntityDataMapper;
    }

    // TODO: add local cache
    @Override
    public Observable<Weather> weather(int weatherId, String units) {
        final WeatherDataStore weatherDataStore = weatherDataStoreFactory.create();
        return weatherDataStore.weatherEntityDetails(weatherId, units)
                .map(weatherEntity -> weatherEntityDataMapper.transform(weatherEntity));
    }
}
