package ru.innopolis.innoweather.data.repository.datasource;

import ru.innopolis.innoweather.data.entity.CityEntity;
import rx.Observable;

public interface CityDataStore {

    Observable<CityEntity> getCity(int cityId);

    Observable<CityEntity> getAllCities();

    Observable<Boolean> addCity(CityEntity city);

    Observable<Boolean> initializeCities();

    Observable<Boolean> removeCity(CityEntity cityEntity);
}
