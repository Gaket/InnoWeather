package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.model.WeatherModel;
import ru.innopolis.innoweather.presentation.presenter.WeatherDetailsPresenter;
import ru.innopolis.innoweather.presentation.view.WeatherDetailsView;

public class WeatherDetailsFragment extends BaseFragment implements WeatherDetailsView {
    private static final String INSTANCE_STATE__PARAM_WEATHER = "RU.INNO_WEATHER.STATE_PARAM_WEATHER";

    @Inject
    WeatherDetailsPresenter weatherDetailsPresenter;

    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_cloudiness)
    TextView tvCloudiness;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    private Unbinder mUnbinder;

    public WeatherDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
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
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        weatherDetailsPresenter.destroy();
    }

    @Override
    public void renderWeatherDetails(WeatherModel weather) {
        if (weather != null) {
            tvTemp.setText(weather.getTemp().toString());
            tvCloudiness.setText(weather.getCloudiness());
        }
    }

    @Override
    public void showProgress() {
        this.rlProgress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideProgress() {
        this.rlProgress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
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
    public boolean update() {
        weatherDetailsPresenter.initialize();
        return true;
    }

    private void loadWeatherDetails() {
        if (weatherDetailsPresenter != null) {
            weatherDetailsPresenter.initialize();
        }
    }


}
