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

    private WeatherDetailsView mViewDetailsView;

    private final UseCase mWeatherDetailsUseCase;
    private final WeatherModelDataMapper mWeatherModelDataMapper;

    @Inject
    public WeatherDetailsPresenter(@Named("weatherDetails") UseCase weatherDetailsUseCase,
                                   WeatherModelDataMapper weatherModelDataMapper) {
        this.mWeatherDetailsUseCase = weatherDetailsUseCase;
        this.mWeatherModelDataMapper = weatherModelDataMapper;
    }

    public void setView(@NonNull WeatherDetailsView view) {
        this.mViewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mWeatherDetailsUseCase.unsubscribe();
        mViewDetailsView = null;
    }

    /**
     * Initializes the presenter by start retrieving Weather details.
     */
    public void initialize() {
        loadWeatherDetails();
    }

    /**
     * Loads Weather details.
     */
    private void loadWeatherDetails() {
        showProgressView();
        getWeatherDetails();
    }

    private void showProgressView() {
        mViewDetailsView.showProgress();
    }

    private void hideProgressView() {
        mViewDetailsView.hideProgress();
    }

    private void showWeatherDetailsInView(Weather weather) {
        final WeatherModel weatherModel = mWeatherModelDataMapper.transform(weather);
        this.mViewDetailsView.renderWeatherDetails(weatherModel);
    }

    /**
     * Gets weather details and shows them in view
     */
    private void getWeatherDetails() {
        mWeatherDetailsUseCase.execute(new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                hideProgressView();
            }

            @Override
            public void onError(Throwable e) {
                hideProgressView();
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(Weather weather) {
                showWeatherDetailsInView(weather);
            }
        });
    }

}
