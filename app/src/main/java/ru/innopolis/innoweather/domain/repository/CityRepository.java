package ru.innopolis.innoweather.domain.repository;

import java.util.List;

import ru.innopolis.innoweather.domain.City;
import rx.Observable;

/**
 * Repository that abstracts way of getting the cities for the app
 */
public interface CityRepository {

    Observable<City> cities();
}
