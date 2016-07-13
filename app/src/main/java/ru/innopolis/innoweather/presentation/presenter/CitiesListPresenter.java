package ru.innopolis.innoweather.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.domain.interactor.UseCase;
import ru.innopolis.innoweather.presentation.di.PerActivity;
import ru.innopolis.innoweather.presentation.mapper.WeatherModelDataMapper;
import ru.innopolis.innoweather.presentation.model.WeatherModel;
import ru.innopolis.innoweather.presentation.view.CitiesListView;
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;
import rx.Subscriber;

@PerActivity
public class CitiesListPresenter implements Presenter {
    private static String TAG = "CitiesListPresenter";

    private CitiesListView citiesListView;

    private final UseCase weatherDetailsUseCase;
    private final WeatherModelDataMapper weatherModelDataMapper;

    @Inject
    public CitiesListPresenter(@Named("weatherDetails") UseCase weatherDetailsUseCase,
                               WeatherModelDataMapper weatherModelDataMapper) {
        this.weatherDetailsUseCase = weatherDetailsUseCase;
        this.weatherModelDataMapper = weatherModelDataMapper;
    }

    public void setView(@NonNull WeatherDetailsView view) {
        this.citiesListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        weatherDetailsUseCase.unsubscribe();
        citiesListView = null;
    }

    /**
     * Initializes the presenter by start retrieving Weather details.
     */
    public void initialize() {
        this.loadWeatherDetails();
    }

    /**
     * Loads Weather details.
     */
    private void loadWeatherDetails() {
        showViewBusy();
        getWeatherDetails();
    }

    private void showViewBusy() {
        citiesListView.showProgress();
    }

    private void hideViewBusy() {
        citiesListView.hideProgress();
    }

    private void showWeatherDetailsInView(Weather weather) {
        final WeatherModel weatherModel = weatherModelDataMapper.transform(weather);
        this.citiesListView.renderWeatherDetails(weatherModel);
    }

    private void getWeatherDetails() {
        this.weatherDetailsUseCase.execute(new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                hideViewBusy();
            }

            @Override
            public void onError(Throwable e) {
                hideViewBusy();
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(Weather weather) {
                showWeatherDetailsInView(weather);
            }
        });
    }

}
