package ru.innopolis.innoweather.presentation.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.presenter.CitiesListPresenter;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;
import ru.innopolis.innoweather.presentation.view.fragment.CityPickerDialogFragment;

public class CitiesListActivity extends BaseActivity implements HasComponent<UserComponent>, CitiesListFragment.CityListListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private UserComponent mUserComponent;

    @Inject
    CitiesListPresenter citiesListPresenter;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cities_list, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @OnClick(R.id.fab)
    public void onClick() {

        FragmentManager manager = getSupportFragmentManager();
        CityPickerDialogFragment cityPickerDialogFragment = new CityPickerDialogFragment();
        cityPickerDialogFragment.show(manager, "dialog");


        }
}
