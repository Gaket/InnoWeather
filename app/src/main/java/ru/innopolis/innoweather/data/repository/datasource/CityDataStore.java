package ru.innopolis.innoweather.data.repository.datasource;

import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.domain.City;
import rx.Observable;

public interface CityDataStore {

    Observable<CityEntity> getCity(int cityId);

    Observable<CityEntity> getAllCities();

    Observable<Boolean> addCity(City city);
}
