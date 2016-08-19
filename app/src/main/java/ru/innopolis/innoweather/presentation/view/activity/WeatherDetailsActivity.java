package ru.innopolis.innoweather.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.CityComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerCityComponent;
import ru.innopolis.innoweather.presentation.di.modules.CityModule;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.view.fragment.WeatherDetailsFragment;

public class WeatherDetailsActivity extends BaseActivity implements HasComponent<CityComponent> {

    private static final String INTENT_EXTRA_PARAM_CITY_ID = "RU.INNO_WEATHER.INTENT_PARAM_CITY_ID";
    private static final String INTENT_EXTRA_PARAM_CITY_NAME = "RU.INNO_WEATHER.INTENT_PARAM_CITY_NAME";
    private static final String INSTANCE_STATE_PARAM_CITY_ID = "RU.INNO_WEATHER.STATE_PARAM_CITY_ID";
    private static final String INSTANCE_STATE_PARAM_CITY_NAME = "RU.INNO_WEATHER.STATE_PARAM_CITY_NAME";

    private int cityId;
    private String cityName;
    private CityComponent cityComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        initializeActivity(savedInstanceState);
        initializeInjector();
        overridePendingTransition(R.anim.right_in, R.anim.disappear);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.appear, R.anim.left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.appear, R.anim.left_out);
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            cityId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CITY_ID, -1);
            cityName = getIntent().getStringExtra(INTENT_EXTRA_PARAM_CITY_NAME);
            Fragment weatherDetails = new WeatherDetailsFragment();
            weatherDetails.setHasOptionsMenu(true);
            addFragment(R.id.fragmentContainer, weatherDetails, getString(R.string.weather_tag));
        } else {
            cityId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CITY_ID);
            cityName = savedInstanceState.getString(INSTANCE_STATE_PARAM_CITY_NAME);
        }
        ButterKnife.bind(this);
        setActionBar();
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cityName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CITY_ID, cityId);
            outState.putString(INSTANCE_STATE_PARAM_CITY_NAME, cityName);
        }
    }

    private void initializeInjector() {
        cityComponent = DaggerCityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .cityModule(new CityModule(cityId))
                .build();
    }

    @Override
    public CityComponent getComponent() {
        return cityComponent;
    }

    public static Intent getCallingIntent(Context context, CityModel cityModel) {
        Intent callingIntent = new Intent(context, WeatherDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CITY_ID, cityModel.getId());
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CITY_NAME, cityModel.getName());

        return callingIntent;
    }

}
