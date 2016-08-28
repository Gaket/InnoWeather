package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.components.CityComponent;
import ru.innopolis.innoweather.presentation.model.WeatherModel;
import ru.innopolis.innoweather.presentation.presenter.WeatherDetailsPresenter;
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;

public class WeatherDetailsFragment extends BaseFragment implements WeatherDetailsView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    WeatherDetailsPresenter weatherDetailsPresenter;

    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_cloudiness)
    TextView tvCloudiness;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_pressure)
    TextView tvPressure;
    @BindView(R.id.tv_wind_speed)
    TextView tvWindSpeed;
    @BindView(R.id.tv_wind_direction)
    TextView tvWindDirection;
    @BindView(R.id.swipe_refresh_weather_details)
    SwipeRefreshLayout swipeRefreshWeatherDetails;
    private Unbinder unbinder;


    public WeatherDetailsFragment() {
        setRetainInstance(true);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(CityComponent.class).inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshWeatherDetails();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            loadWeatherDetails();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            weatherDetailsPresenter.initialize();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        // Handle presses on the action bar items
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        weatherDetailsPresenter.resume();
    }


    @Override
    public void onPause() {
        super.onPause();
        this.weatherDetailsPresenter.pause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        weatherDetailsPresenter.destroy();
    }


    @Override
    public void renderWeatherDetails(WeatherModel weather) {
        if (weather != null) {
            String temp = String.format(Locale.US, "%d°С", weather.getTemp().intValue());
            tvTemp.setText(temp);
            tvCloudiness.setText(capitalize(weather.getCloudiness()));
            String humidity = String.format(Locale.US, "%d%%", weather.getHumidity().intValue());
            tvHumidity.setText(humidity);
            String pressure = String.format(Locale.US, "%d %s", weather.getPressure().intValue(), getActivity().getString(R.string.mbar));
            tvPressure.setText(pressure);
            String windSpeed = String.format(Locale.US, "%d %s", weather.getWindSpeed().intValue(), getActivity().getString(R.string.msec));
            tvWindSpeed.setText(windSpeed);
            String windDirection = String.format(Locale.US, "%s", weather.getWindDirection());
            tvWindDirection.setText(windDirection);
        }
    }

    @Override
    public void showMessage(String msg) {
        showToastMessage(msg);
    }


    @Override
    public void showProgress() {
        swipeRefreshWeatherDetails.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshWeatherDetails.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }


    @Override
    public void onRefresh() {
        weatherDetailsPresenter.update();
    }

    private void swipeRefreshWeatherDetails() {
        swipeRefreshWeatherDetails.setOnRefreshListener(this);
        swipeRefreshWeatherDetails.setColorSchemeResources(R.color.primary, R.color.primary_light);
    }

    private void loadWeatherDetails() {
        if (weatherDetailsPresenter != null) {
            weatherDetailsPresenter.initialize();
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
