package ru.innopolis.innoweather.data.repository.datasource;

import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

public interface WeatherDataStore {

    Observable<WeatherEntity> weatherEntityDetails(int cityId);
}
