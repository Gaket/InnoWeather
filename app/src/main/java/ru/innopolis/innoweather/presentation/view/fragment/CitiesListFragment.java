package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.presenter.CitiesListPresenter;
import ru.innopolis.innoweather.presentation.view.CitiesListView;
import ru.innopolis.innoweather.presentation.view.adapter.CitiesAdapter;

/**
 * A fragment representing a list of cities.
 */
public class CitiesListFragment extends BaseFragment implements CitiesListView {

    /**
     * Interface for listening user list events.
     */
    public interface CityListListener {
        void onCityClicked(final CityModel cityModel);
        void onUpdateClicked();
    }

    @Inject
    CitiesListPresenter citiesListPresenter;
    @Inject
    CitiesAdapter citiesAdapter;

    @BindView(R.id.rv_users)
    RecyclerView rv_users;
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    private CitiesAdapter.OnItemClickListener onItemClickListener = cityModel -> {
        if (citiesListPresenter != null && cityModel != null) {
            citiesListPresenter.onCityClicked(cityModel);
        }
    };

    private CityListListener cityListListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CitiesListFragment() {
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CityListListener) {
            this.cityListListener = (CityListListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        citiesListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadCitiesList();
        }
    }

    private void loadCitiesList() {
        citiesListPresenter.initialize();
    }

    private void setupRecyclerView() {
        citiesAdapter.setOnItemClickListener(onItemClickListener);
        rv_users.setAdapter(citiesAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cityListListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        citiesListPresenter.destroy();
    }

    @Override
    public void renderCitiesList(Collection<CityModel> cityModels) {
        if (cityModels != null) {
            citiesAdapter.setCitiesCollection(cityModels);
            citiesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void viewWeather(CityModel cityModel) {
        if (cityModel != null) {
            cityListListener.onCityClicked(cityModel);
        }

    }

    @Override
    public void showProgress() {
        rl_progress.setVisibility(View.VISIBLE);
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideProgress() {
        rl_progress.setVisibility(View.GONE);
        getActivity().setProgressBarIndeterminateVisibility(false);

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public boolean update() {
        citiesListPresenter.initialize();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
                citiesListPresenter.initialize();

        }
        return super.onOptionsItemSelected(item);
    }

}
