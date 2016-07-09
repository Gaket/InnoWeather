package ru.innopolis.innoweather;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.innopolis.innoweather.data.entity.Weather;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.data.net.RestAPI;
import ru.innopolis.innoweather.data.net.ServiceGenerator;
import rx.Observable;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class WeatherApiTest {

    private Observable<WeatherEntity> weatherEntityObservable;

    @Before
    public void init() {
        RestAPI service = new ServiceGenerator().createService(RestAPI.class);
        weatherEntityObservable = service.getCityWeatherByCityId(2172797);
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
                    // If all the values are 0.0, then probably we didn't get the real data
                    // There is low probability, that all of these values will be equal zero at the same time
                    assertTrue(
                            weather.getMain().getTempMin() != 0.0
                                    || weather.getMain().getTempMax() != 0.0
                                    || weather.getMain().getTemp() != 0.0);
                },
                (e) -> fail(e.getMessage())
        );
    }

    @Test
    public void testGetWeatherByCityId() {

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