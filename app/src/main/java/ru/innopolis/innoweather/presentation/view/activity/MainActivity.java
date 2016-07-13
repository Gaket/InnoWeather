package ru.innopolis.innoweather.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerWeatherComponent;
import ru.innopolis.innoweather.presentation.di.components.WeatherComponent;
import ru.innopolis.innoweather.presentation.di.modules.WeatherModule;
import ru.innopolis.innoweather.presentation.view.fragment.WeatherDetailsFragment;

public class MainActivity extends BaseActivity implements HasComponent<WeatherComponent> {

    private static final String INTENT_EXTRA_PARAM_CITY_ID = "RU.INNO_WEATHER.INTENT_PARAM_CITY_ID";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int cityId;
    private WeatherComponent weatherComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: look at what requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); is
        ButterKnife.bind(this);
        initializeActivity(savedInstanceState);
        initializeInjector();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // TODO: remove default cityid
            this.cityId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CITY_ID, 2172797);
            addFragment(R.id.fragmentContainer, new WeatherDetailsFragment());
        } else {
            // restore state
        }
    }


    private void initializeInjector() {
        this.weatherComponent = DaggerWeatherComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .weatherModule(new WeatherModule(this.cityId))
                .build();
    }

    @Override
    public WeatherComponent getComponent() {
        return weatherComponent;
    }

}
