package ru.innopolis.innoweather.domain.repository;

import ru.innopolis.innoweather.domain.Weather;
import rx.Observable;

/**
 * Repository that abstracts way of getting the {@link Weather}  data: cloud, cashed, etc.
 */
public interface WeatherRepository {

    Observable<Weather> weather(int cityId);
}
