package ru.innopolis.innoweather.presentation.view;

import java.util.List;

import ru.innopolis.innoweather.presentation.model.CityModel;

public interface CitiesListView extends LoadDataView {

  void renderCitiesList (List<CityModel> cityModels);

  void viewWeather (CityModel cityModel);
}
