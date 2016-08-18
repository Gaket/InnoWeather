package ru.innopolis.innoweather.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.presentation.model.WeatherModel;

/**
 * Mapper class used to transform {@link Weather} (in the domain layer) to {@link WeatherModel} in the
 * presentation layer.
 */
public class WeatherModelDataMapper {

    @Inject
    public WeatherModelDataMapper() {
    }

    public WeatherModel transform(Weather weather) {
        if (weather == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        WeatherModel weatherModel = new WeatherModel(weather.getCityId());
        weatherModel.setTemp(weather.getTemp());
        weatherModel.setTempMax(weather.getTempMax());
        weatherModel.setTempMin(weather.getTempMin());
        weatherModel.setPressure(weather.getPressure());
        weatherModel.setHumidity(weather.getHumidity());
        weatherModel.setCloudiness(weather.getCloudiness());
        weatherModel.setWindDirection(weather.getWindDirection());
        weatherModel.setWindSpeed(weather.getWindSpeed());

        return weatherModel;
    }

    public Collection<WeatherModel> transform(Collection<Weather> weathersCollection) {
        Collection<WeatherModel> weatherModelsCollection;

        if (weathersCollection != null && !weathersCollection.isEmpty()) {
            weatherModelsCollection = new ArrayList<>();
            for (Weather Weather : weathersCollection) {
                weatherModelsCollection.add(transform(Weather));
            }
        } else {
            weatherModelsCollection = Collections.emptyList();
        }

        return weatherModelsCollection;
    }
}
