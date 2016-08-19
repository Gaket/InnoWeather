package ru.innopolis.innoweather.data.net;

import org.junit.Before;
import org.junit.Test;

import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.data.entity.internals.Weather;
import rx.Observable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RestApiImplTest {

    private Observable<WeatherEntity> weatherEntityObservable;

    @Before
    public void init() {
        RestApi restApi = new RestApiImpl();
        weatherEntityObservable = restApi.getCityWeatherByCityId(524901, "RU"); // Moscow
        // Try to print weather once for manual testing
    }

    @Test
    public void printWeatherForManualTesting() {
        weatherEntityObservable.subscribe(
                (weather) -> System.out.println(weather),
                (e) -> fail(e.getMessage())
        );
    }


    @Test
    public void testGetTempByCityId() {

        weatherEntityObservable.subscribe(
                (weather) -> {
                    // Default value for these values is 0.0
                    // There is low probability, that all of these values will be equal zero at the same time
                    // If all the values are 0.0, then probably we didn't get the real data
                    assertTrue(
                            weather.getMain().getTempMin() != 0.0
                                    || weather.getMain().getTempMax() != 0.0
                                    || weather.getMain().getTemp() != 0.0);
                },
                (e) -> fail(e.getMessage())
        );
    }


    @Test
    public void testGetWeatherDescriptionByCityId() {

        weatherEntityObservable.subscribe(
                (weather) -> {
                    Weather curWeather = weather.getWeather().get(0);
                    assertFalse(curWeather.getDescription().isEmpty()
                            || curWeather.getShortInfo().isEmpty());
                },
                (e) -> fail(e.getMessage())
        );
    }
}