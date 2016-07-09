package ru.innopolis.innoweather.data.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestAPI {

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link WeatherEntity}.
     *
     * @param cityId The city id used to get weather data.
     */
    @GET("weather")
    Observable<WeatherEntity> getCityWeatherByCityId(@Query("id") Integer cityId);
}
