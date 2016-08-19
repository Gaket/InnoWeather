package ru.innopolis.innoweather.data.repository.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import ru.innopolis.innoweather.data.cache.Cache;
import ru.innopolis.innoweather.data.entity.CityEntity;
import rx.Observable;

public class LocalCityDataStore implements CityDataStore {
    private static final String TAG = "LocalCityDataStore";

    private final Cache cache;

    public LocalCityDataStore(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Observable<CityEntity> getCity(final int cityId) {
        return cache.get(cityId);
    }

    @Override
    public Observable<CityEntity> getAllCities() {
        return cache.getAll();
    }

    public Observable<Boolean> addCity(CityEntity cityEntity) {
        cache.put(cityEntity);
        return Observable.just(Boolean.TRUE);
    }

    @Override
    public Observable<Boolean> initializeCities() {
        List<CityEntity> entities = getCityEntities(new StringReader(initialData));
        for (CityEntity entity : entities) {
            cache.put(entity);
        }
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> removeCity(CityEntity cityEntity) {
        boolean result = cache.remove(cityEntity);
        return Observable.just(result);
    }

    private List<CityEntity> getCityEntities(Reader reader) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<CityEntity>>() {
        }.getType();
        return gson.fromJson(reader, collectionType);
    }

    private static final String initialData = "[" +
            "{id:524901,name:Moscow,country:RU}," +
            "{id:551487,name:Kazan,country:RU}," +
            "{id:479561,name:Ufa,country:RU}," +
            "{id:1508291,name:Chelyabinsk,country:RU}," +
            "{id:554234,name:Kaliningrad,country:RU}," +
            "{id:491422,name:Sochi,country:RU}," +
            "{id:1496747,name:Novosibirsk,country:RU}" +
            "]";
}
