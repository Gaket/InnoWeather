package ru.innopolis.innoweather.data.repository.datasource;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import ru.innopolis.innoweather.data.entity.CityEntity;
import rx.Observable;

public class LocalCityDataStore implements CityDataStore {
    private static final String TAG = "LocalCityDataStore";

    @Override
    public Observable<List<CityEntity>> cities() {
        Reader initialData = new StringReader(LocalCityDataStore.initialData);
        List<CityEntity> cities = getCityEntities(initialData);
        return Observable.just(cities);
    }

    private List<CityEntity> getCityEntities(Reader reader) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<CityEntity>>() {
        }.getType();
        return gson.fromJson(reader, collectionType);
    }

    private static final String initialData = "[" +
            "{_id:743615,name:Kazan,country:TR,coord:{lon:32.683891,lat:40.23167}}, " +
            "{_id:524901,name:Moscow,country:RU,coord:{lon:37.615555,lat:55.75222}}" +
            "]";
}
