package ru.innopolis.innoweather.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.data.net.NetworkChecker;
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
    private final Context context;

    @Inject
    public WeatherDetailsPresenter(@Named("weatherDetails") UseCase weatherDetailsUseCase,
                                   WeatherModelDataMapper weatherModelDataMapper, Context context) {
        this.weatherDetailsUseCase = weatherDetailsUseCase;
        this.weatherModelDataMapper = weatherModelDataMapper;
        this.context = context;
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
        viewDetailsView.showProgress();
    }

    private void hideProgressView() {
        viewDetailsView.hideProgress();
    }

    private void showWeatherDetailsInView(Weather weather) {
        final WeatherModel weatherModel = weatherModelDataMapper.transform(weather);
        this.viewDetailsView.renderWeatherDetails(weatherModel);
    }

    public void update() {
        if (NetworkChecker.isNetworkActive(context)) {
            loadWeatherDetails();
            viewDetailsView.showMessage(context.getString(R.string.msg_dowload_success));
        } else {
            viewDetailsView.showMessage(context.getString(R.string.msg_network_unavailable));
        }
    }

    /**
     * Gets weather details and shows them in view
     */
    private void getWeatherDetails() {
        weatherDetailsUseCase.execute(new Subscriber<Weather>() {
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
