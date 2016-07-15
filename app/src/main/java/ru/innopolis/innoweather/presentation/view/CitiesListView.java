package ru.innopolis.innoweather.presentation.view;

import java.util.Collection;

import ru.innopolis.innoweather.presentation.model.CityModel;

public interface CitiesListView extends LoadDataView {

    /**
     * Render a cities list in the UI
     *
     * @param cityModels
     */
    void renderCitiesList(Collection<CityModel> cityModels);

    /**
     * Show weather in the given city
     *
     * @param cityModel
     */
    void viewWeather(CityModel cityModel);
}
