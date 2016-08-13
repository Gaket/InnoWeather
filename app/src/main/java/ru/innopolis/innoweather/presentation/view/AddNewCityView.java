package ru.innopolis.innoweather.presentation.view;

public interface AddNewCityView extends LoadDataView {

    /**
     * Allow user to choose new city
     *
     * @return id of the city that user chose
     */
    int chooseNewCity();

}
