package ru.innopolis.innoweather.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerUserComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.di.modules.UserModule;
import ru.innopolis.innoweather.presentation.view.fragment.WeatherDetailsFragment;

public class WeatherDetailsActivity extends BaseActivity implements HasComponent<UserComponent> {

    private static final String INTENT_EXTRA_PARAM_CITY_ID = "RU.INNO_WEATHER.INTENT_PARAM_CITY_ID";
    private static final String INSTANCE_STATE_PARAM_CITY_ID = "RU.INNO_WEATHER.STATE_PARAM_USER_ID";

    private int mCityId;
    private UserComponent mUserComponent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        initializeActivity(savedInstanceState);
        initializeInjector();
    }


    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            ButterKnife.bind(this);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mCityId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CITY_ID, -1);
            addFragment(R.id.fragmentContainer, new WeatherDetailsFragment());
        } else {
            mCityId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CITY_ID);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CITY_ID, mCityId);
        }
        super.onSaveInstanceState(outState);
    }


    private void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule(mCityId))
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }

    public static Intent getCallingIntent(Context context, int cityId) {
        Intent callingIntent = new Intent(context, WeatherDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CITY_ID, cityId);
        return callingIntent;
    }

}
