package ru.innopolis.innoweather.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.presentation.model.CityModel;

/**
 * Mapper class used to transform {@link City} (in the domain layer) to {@link CityModel} in the
 * presentation layer.
 */
public class CityModelDataMapper {

    @Inject
    public CityModelDataMapper() {
    }

    public CityModel transform(City city) {
        if (city == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CityModel cityModel = new CityModel(city.getId());
        cityModel.setCountry(city.getCountry());
        cityModel.setName(city.getName());

        return cityModel;
    }

    public Collection<CityModel> transform(Collection<City> citysCollection) {
        Collection<CityModel> cityModelsCollection;

        if (citysCollection != null && !citysCollection.isEmpty()) {
            cityModelsCollection = new ArrayList<>();
            for (City City : citysCollection) {
                cityModelsCollection.add(transform(City));
            }
        } else {
            cityModelsCollection = Collections.emptyList();
        }

        return cityModelsCollection;
    }
}
