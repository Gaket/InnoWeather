package ru.innopolis.innoweather.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;

public class CitiesListActivity extends BaseActivity implements HasComponent<UserComponent>, CitiesListFragment.CityListListener {


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CitiesListActivity.class);
    }

    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new CitiesListFragment());
        }
    }


    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    @Override
    public void onCityClicked(CityModel cityModel) {
        navigator.navigateToWeatherDetails(this, cityModel.getId());
    }
}
