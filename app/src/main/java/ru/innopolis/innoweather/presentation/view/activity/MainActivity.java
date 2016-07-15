package ru.innopolis.innoweather.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.di.modules.UserModule;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;
import ru.innopolis.innoweather.presentation.view.fragment.WeatherDetailsFragment;

public class MainActivity extends BaseActivity implements HasComponent<UserComponent>, CitiesListFragment.CityListListener {

    private static final String INTENT_EXTRA_PARAM_CITY_ID = "RU.INNO_WEATHER.INTENT_PARAM_CITY_ID";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int cityId;
    private UserComponent userComponent;

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
            this.cityId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CITY_ID, -1);
            addFragment(R.id.fragmentContainer, new CitiesListFragment());
        } else {
            // restore state
        }
    }


    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule(this.cityId))
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    @Override
    public void onCityClicked(CityModel cityModel) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, new WeatherDetailsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
