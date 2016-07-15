package ru.innopolis.innoweather.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.di.modules.UserModule;
import ru.innopolis.innoweather.presentation.view.fragment.CitiesListFragment;
import ru.innopolis.innoweather.presentation.view.fragment.WeatherDetailsFragment;

public class WeatherDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

    private static final String INTENT_EXTRA_PARAM_CITY_ID = "RU.INNO_WEATHER.INTENT_PARAM_CITY_ID";
    private static final String INSTANCE_STATE_PARAM_USER_ID = "org.android10.STATE_PARAM_USER_ID";

    public static Intent getCallingIntent(Context context, int cityId) {
        Intent callingIntent = new Intent(context, WeatherDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CITY_ID, cityId);
        return callingIntent;
    }

    private int cityId;
    private UserComponent userComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        // TODO: look at what requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); is
        initializeActivity(savedInstanceState);
        initializeInjector();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // TODO: remove default cityid
            this.cityId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CITY_ID, -1);
            addFragment(R.id.fragmentContainer, new WeatherDetailsFragment());
        } else {
            this.cityId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_USER_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_USER_ID, this.cityId);
        }
        super.onSaveInstanceState(outState);
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

}
