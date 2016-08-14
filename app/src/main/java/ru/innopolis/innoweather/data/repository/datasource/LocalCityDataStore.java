package ru.innopolis.innoweather.data.repository.datasource;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.CityCache;
import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.domain.City;
import rx.Observable;

public class LocalCityDataStore implements CityDataStore {
    private static final String TAG = "LocalCityDataStore";

    private final CityCache cityCache;

    public LocalCityDataStore(CityCache cityCache) {
        this.cityCache = cityCache;
    }

    @Override
    public Observable<CityEntity> getCity(final int cityId) {
        return cityCache.get(cityId);
    }

    @Override
    public Observable<CityEntity> getAllCities() {
        return cityCache.getAll();
    }




    public Observable<Boolean> addCity(City city) {
        return Observable.just(Boolean.TRUE);
    }

    @Override
    public Observable<Boolean> initializeCities() {
        List<CityEntity> entities = getCityEntities(new StringReader(initialData));
        for (CityEntity entity : entities) {
            cityCache.put(entity);
        }
        return Observable.just(true);
    }

    private List<CityEntity> getCityEntities(Reader reader) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<CityEntity>>() {
        }.getType();
        return gson.fromJson(reader, collectionType);
    }

    private static final String initialData = "[" +
            "{id:1508291,name:Chelyabinsk,country:RU}," +
            "{id:551487,name:Kazan,country:RU}," +
            "{id:524901,name:Moscow,country:RU}" +
            "]";
}
