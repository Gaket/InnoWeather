package ru.innopolis.innoweather.data.net;

import retrofit2.http.Query;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

public class RestApiImpl implements RestApi {
    private static final String TAG = "RestApiImpl";

    RestApi restApi;

    public RestApiImpl() {
        restApi = new ServiceGenerator().createService(RestApi.class);
    }

    @Override
    public Observable<WeatherEntity> getCityWeatherByCityId(@Query("id") Integer cityId,
                                                            @Query("units") String units) {
        return restApi.getCityWeatherByCityId(cityId, units);
    }
}
