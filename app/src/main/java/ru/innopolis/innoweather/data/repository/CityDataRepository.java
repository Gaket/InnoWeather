package ru.innopolis.innoweather.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.mapper.CityEntityDataMapper;
import ru.innopolis.innoweather.data.repository.datasource.CityDataStore;
import ru.innopolis.innoweather.data.repository.datasource.CityDataStoreFactory;
import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.repository.CityRepository;
import rx.Observable;

/**
 * Repository that decides if Cloud or Local datastore should be used
 */
@Singleton
public class CityDataRepository implements CityRepository {
    private static final String TAG = "CityDataRepository";

    private final CityDataStoreFactory factory;
    private final CityEntityDataMapper cityEntityDataMapper;

    @Inject
    public CityDataRepository(CityDataStoreFactory factory, CityEntityDataMapper cityEntityDataMapper) {
        this.factory = factory;
        this.cityEntityDataMapper = cityEntityDataMapper;
    }

    @Override
    public Observable<City> getActiveCities() {
        // At the moment, we work only with local data store
        CityDataStore cityDataStore = factory.createLocalDataStore();
        return cityDataStore.getAllCities()
                .map(cityEntities -> cityEntityDataMapper.transform(cityEntities));
    }

    @Override
    public Observable<Boolean> addNewCity(City city) {
        CityDataStore cityDataStore = factory.createLocalDataStore();
        return cityDataStore.addCity(city);
    }


}
