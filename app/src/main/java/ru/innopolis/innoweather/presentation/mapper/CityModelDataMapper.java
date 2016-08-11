package ru.innopolis.innoweather.presentation.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.presentation.model.CityModel;

/**
 * Mapper class used to transform {@link City} (in the domain layer) to {@link CityModel} in the
 * presentation layer.
 */
public class CityModelDataMapper {
    private static final String TAG = "CityModelDataMapper";

    @Inject
    public CityModelDataMapper() {
    }

    public CityModel transform(City city) {
        return transform(city, null);
    }

    public CityModel transform(City city, Weather weather) {
        if (city == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CityModel cityModel = new CityModel(city.getId());
        cityModel.setCountry(city.getCountry());
        cityModel.setName(city.getName());

        if (weather != null) {
            cityModel.setTemp(weather.getTemp());
        }
        return cityModel;
    }


    public Collection<CityModel> transform(Collection<City> citiesCollection) {
        Collection<CityModel> cityModelsCollection;

        if (citiesCollection != null && !citiesCollection.isEmpty()) {
            cityModelsCollection = new ArrayList<>();
            for (City City : citiesCollection) {
                cityModelsCollection.add(transform(City));
            }
        } else {
            cityModelsCollection = Collections.emptyList();
        }

        return cityModelsCollection;
    }

    public Collection<CityModel> transform(Collection<City> citiesCollection, Collection<Weather> weatherCollection) {
        Collection<CityModel> cityModelsCollection = new ArrayList<>();
        if (weatherCollection == null || weatherCollection.isEmpty()) {
            for (City city : citiesCollection) {
                cityModelsCollection.add(transform(city, null));
            }
        } else {
            Map<Integer, Weather> weatherMap = new HashMap<>();
            for (Weather weather : weatherCollection) {
                weatherMap.put(weather.getCityId(), weather);
            }
            for (City city : citiesCollection) {
                cityModelsCollection.add(transform(city, weatherMap.get(city.getId())));
            }
        }
        return cityModelsCollection;
    }
}
