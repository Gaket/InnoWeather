package ru.innopolis.innoweather.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.mapper.CityEntityDataMapper;
import ru.innopolis.innoweather.data.repository.datasource.CityDataStore;
import ru.innopolis.innoweather.data.repository.datasource.CloudWeatherDataStore;
import ru.innopolis.innoweather.data.repository.datasource.LocalCityDataStore;
import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.repository.CityRepository;
import rx.Observable;

/**
 * Repository that decides if Cloud or Local datastore should be used
 */
@Singleton
public class CityDataRepository implements CityRepository {
    private static final String TAG = "CityDataRepository";

    private final LocalCityDataStore cityDataStore;
    private final CityEntityDataMapper cityEntityDataMapper;

    @Inject
    public CityDataRepository(LocalCityDataStore cityDataStore, CityEntityDataMapper cityEntityDataMapper) {
        this.cityDataStore = cityDataStore;
        this.cityEntityDataMapper = cityEntityDataMapper;
    }

    @Override
    public Observable<City> cities() {
        return cityDataStore.cities()
                .map(cityEntities -> cityEntityDataMapper.transform(cityEntities));
    }
}
