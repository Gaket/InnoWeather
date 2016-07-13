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
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;
import rx.Subscriber;

@PerActivity
public class WeatherDetailsPresenter implements Presenter {
    private static String TAG = "WeatherDetailsPresenter";

    private WeatherDetailsView viewDetailsView;

    private final UseCase weatherDetailsUseCase;
    private final WeatherModelDataMapper weatherModelDataMapper;

    @Inject
    public WeatherDetailsPresenter(@Named("weatherDetails") UseCase weatherDetailsUseCase,
                                   WeatherModelDataMapper weatherModelDataMapper) {
        this.weatherDetailsUseCase = weatherDetailsUseCase;
        this.weatherModelDataMapper = weatherModelDataMapper;
    }

    public void setView(@NonNull WeatherDetailsView view) {
        this.viewDetailsView = view;
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
        viewDetailsView = null;
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
        viewDetailsView.showProgress();
    }

    private void hideViewBusy() {
        viewDetailsView.hideProgress();
    }

    private void showWeatherDetailsInView(Weather weather) {
        final WeatherModel weatherModel = weatherModelDataMapper.transform(weather);
        this.viewDetailsView.renderWeatherDetails(weatherModel);
    }

    private void getWeatherDetails() {
        weatherDetailsUseCase.execute(new Subscriber<Weather>() {
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
