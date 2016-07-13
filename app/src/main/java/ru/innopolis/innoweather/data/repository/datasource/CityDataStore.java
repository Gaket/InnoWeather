package ru.innopolis.innoweather.data.repository.datasource;

import java.util.List;

import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

public interface CityDataStore {
    Observable<List<CityEntity>> cities ();
}
