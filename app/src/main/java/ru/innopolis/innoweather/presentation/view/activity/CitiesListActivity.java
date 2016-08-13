package ru.innopolis.innoweather.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.presenter.AddNewCityPresenter;
import ru.innopolis.innoweather.presentation.presenter.CitiesListPresenter;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;
import ru.innopolis.innoweather.presentation.view.fragment.AddNewCityDialogFragment;

public class CitiesListActivity extends BaseActivity implements HasComponent<UserComponent>, CitiesListFragment.CityListListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    CitiesListPresenter citiesListPresenter;
    private UserComponent mUserComponent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new CitiesListFragment());
        }
    }


    private void initializeInjector() {
        this.mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }

    @Override
    public void onCityClicked(CityModel cityModel) {
        navigator.navigateToWeatherDetails(this, cityModel.getId());
    }

    @Override
    public void onUpdateClicked() {
    }


    @OnClick(R.id.fab)
    public void onClick() {
        FragmentManager manager = getSupportFragmentManager();
        AddNewCityDialogFragment addNewCityDialogFragment = new AddNewCityDialogFragment();
        addNewCityDialogFragment.show(manager, "dialog");
    }
}
