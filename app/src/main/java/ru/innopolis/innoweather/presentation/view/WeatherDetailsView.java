package ru.innopolis.innoweather.presentation.view;

import ru.innopolis.innoweather.presentation.model.WeatherModel;

public interface WeatherDetailsView extends LoadDataView {

  void renderWeather(WeatherModel weather);
}
