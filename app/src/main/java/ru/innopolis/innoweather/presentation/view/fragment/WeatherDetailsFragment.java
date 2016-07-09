package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.model.WeatherModel;
import ru.innopolis.innoweather.presentation.presenter.WeatherDetailsPresenter;
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;

public class WeatherDetailsFragment extends Fragment implements WeatherDetailsView {

    @Inject
    WeatherDetailsPresenter userDetailsPresenter;

    public WeatherDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void renderWeather(WeatherModel weather) {

    }

    @Override
    public void showBusy() {

    }

    @Override
    public void hideBusy() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }


}
