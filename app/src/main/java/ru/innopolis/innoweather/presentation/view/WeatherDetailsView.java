package ru.innopolis.innoweather.presentation.view;

import ru.innopolis.innoweather.presentation.model.WeatherModel;

public interface WeatherDetailsView extends LoadDataView {

    /**
     * Render weather details in the UI
     *
     * @param weather
     */
    void renderWeatherDetails(WeatherModel weather);

    /**
     * Show message
     *
     * @param msg
     */
    void showMessage(String msg);
}
