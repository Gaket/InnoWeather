package ru.innopolis.innoweather.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.domain.interactor.UseCase;
import ru.innopolis.innoweather.presentation.mapper.WeatherModelDataMapper;
import ru.innopolis.innoweather.presentation.model.WeatherModel;
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;
import rx.Subscriber;

public class WeatherDetailsPresenter implements Presenter {

    private WeatherDetailsView viewDetailsView;

    private final UseCase getWeatherDetailsUseCase;
    private final WeatherModelDataMapper weatherModelDataMapper;

    @Inject
    public WeatherDetailsPresenter(@Named("WeatherDetails") UseCase getWeatherDetailsUseCase,
                                   WeatherModelDataMapper weatherModelDataMapper) {
        this.getWeatherDetailsUseCase = getWeatherDetailsUseCase;
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
        this.getWeatherDetailsUseCase.unsubscribe();
        this.viewDetailsView = null;
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
        this.showViewBusy();
        this.getWeatherDetails();
    }

    private void showViewBusy() {
        this.viewDetailsView.showBusy();
    }

    private void hideViewBusy() {
        this.viewDetailsView.hideBusy();
    }

    private void showWeatherDetailsInView(Weather weather) {
        final WeatherModel weatherModel = this.weatherModelDataMapper.transform(weather);
        this.viewDetailsView.renderWeather(weatherModel);
    }

    private void getWeatherDetails() {
        this.getWeatherDetailsUseCase.execute(new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                WeatherDetailsPresenter.this.hideViewBusy();
            }

            @Override
            public void onError(Throwable e) {
                WeatherDetailsPresenter.this.hideViewBusy();
            }

            @Override
            public void onNext(Weather weather) {
                WeatherDetailsPresenter.this.showWeatherDetailsInView(weather);
            }
        });
    }

}
