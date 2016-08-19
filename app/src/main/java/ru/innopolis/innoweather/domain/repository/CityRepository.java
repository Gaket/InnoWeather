package ru.innopolis.innoweather.domain.repository;

import ru.innopolis.innoweather.domain.City;
import rx.Observable;

/**
 * Repository that abstracts way of getting the cities for the app
 */
public interface CityRepository {

    Observable<City> getActiveCities();

    Observable<Boolean> addNewCity(City city);

    Observable<Boolean> initializeCities();

    Observable<Boolean> removeCity(City city);
}
