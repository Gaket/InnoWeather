package ru.innopolis.innoweather.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.CityComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerCityComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.presenter.CitiesListPresenter;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;

public class CitiesListActivity extends BaseActivity implements HasComponent<CityComponent>, CitiesListFragment.CityListListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    CitiesListPresenter citiesListPresenter;
    private CityComponent cityComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment cities = new CitiesListFragment();
            cities.setHasOptionsMenu(true);
            addFragment(R.id.fragmentContainer, cities, getString(R.string.cities_tag));
        }
        initializeInjector();
    }

    private void initializeInjector() {
        cityComponent = DaggerCityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public CityComponent getComponent() {
        return cityComponent;
    }

    @Override
    public void onCityClicked(CityModel cityModel) {
        navigator.navigateToWeatherDetails(this, cityModel);
    }


    @OnClick(R.id.fab)
    public void onClick() {
        FragmentManager manager = getSupportFragmentManager();
        CitiesListFragment fragment = (CitiesListFragment) manager.findFragmentByTag(getString(R.string.cities_tag));
        fragment.showAddNewCityScreen();
    }
}
