package ru.innopolis.innoweather.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.mapper.CityEntityDataMapper;
import ru.innopolis.innoweather.data.repository.datasource.CityDataStore;
import ru.innopolis.innoweather.domain.City;
import rx.Observable;

/**
 * Repository that decides if Cloud or Local datastore should be used
 */
@Singleton
public class CityRepository implements ru.innopolis.innoweather.domain.repository.CityRepository {
    private static final String TAG = "CityRepository";

    private final CityDataStore cityDataStore;
    private final CityEntityDataMapper cityEntityDataMapper;

    @Inject
    public CityRepository(CityDataStore cityDataStore, CityEntityDataMapper cityEntityDataMapper) {
        this.cityDataStore = cityDataStore;
        this.cityEntityDataMapper = cityEntityDataMapper;
    }

    @Override
    public Observable<List<City>> cities() {
        return cityDataStore.cities()
                .map(cityEntities -> cityEntityDataMapper.transform(cityEntities));
    }
}
