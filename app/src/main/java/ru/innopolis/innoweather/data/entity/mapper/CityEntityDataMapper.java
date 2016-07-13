package ru.innopolis.innoweather.data.entity.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.domain.City;

public class CityEntityDataMapper {
    private static final String TAG = "CityEntityDataMapper";

    @Inject
    public CityEntityDataMapper() {
    }

    public City transform(CityEntity cityEntity) {
        City city = null;
        if (cityEntity != null) {
            city = new City(cityEntity.getId());
            city.setCountry(cityEntity.getCountry());
            city.setName(cityEntity.getName());
        } else {
            Log.e(TAG, "transform: problem with downloaded cityEntity");
        }
        return city;
    }

    public List<City> transform(Collection<CityEntity> CityEntityCollection) {
        List<City> cityList = new ArrayList<>();
        City City;
        for (CityEntity CityEntity : CityEntityCollection) {
            City = transform(CityEntity);
            if (City != null) {
                cityList.add(City);
            }
        }

        return cityList;
    }
}