package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.components.CityComponent;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.presenter.CitiesListPresenter;
import ru.innopolis.innoweather.presentation.view.CitiesListView;
import ru.innopolis.innoweather.presentation.view.adapter.CitiesAdapter;
import ru.innopolis.innoweather.presentation.view.adapter.DividerItemDecoration;

/**
 * A fragment representing a list of cities.
 */
public class CitiesListFragment extends BaseFragment implements CitiesListView, SwipeRefreshLayout.OnRefreshListener{

    @Inject
    CitiesListPresenter citiesListPresenter;
    @Inject
    CitiesAdapter citiesAdapter;
    @BindView(R.id.rv_cities)
    RecyclerView rvUsers;
    @BindView(R.id.swipe_refresh_cities)
    SwipeRefreshLayout swipeRefreshCities;

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
            cityListListener = (CityListListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(CityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        setupSwipeRefreshLayout();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        citiesListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadCitiesList();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Make sure fragment codes match up
        if (requestCode == AddNewCityDialogFragment.REQUEST_CODE) {
            String editTextString = data.getStringExtra(
                    AddNewCityDialogFragment.EDIT_TEXT_BUNDLE_KEY);
            if (editTextString.equals("success")) {
                citiesListPresenter.update();
            }
        }
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
        }
    }

    @Override
    public void viewWeather(CityModel cityModel) {
        if (cityModel != null) {
            cityListListener.onCityClicked(cityModel);
        }
    }

    @Override
    public void showMessage(String msg) {
        showToastMessage(msg);
    }

    @Override
    public void showProgress() {
        swipeRefreshCities.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshCities.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public void showAddNewCityScreen() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        AddNewCityDialogFragment addNewCityDialogFragment = new AddNewCityDialogFragment();
        addNewCityDialogFragment.setTargetFragment(this, AddNewCityDialogFragment.REQUEST_CODE);
        addNewCityDialogFragment.show(manager, "dialog");
    }

    /**
     * Interface for listening user list events.
     */
    @Override
    public void onRefresh() {
        citiesListPresenter.update();
    }

    private void loadCitiesList() {
        citiesListPresenter.initialize();
    }

    private void setupRecyclerView() {
        LinearLayoutManager llm;
        if (getActivity().getResources().getBoolean(R.bool.landscape_mode)) {
            llm = new GridLayoutManager(getContext(), 2);
        } else {
            llm = new LinearLayoutManager(getContext());
        }
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUsers.setLayoutManager(llm);
        rvUsers.setHasFixedSize(true);
        rvUsers.setAdapter(citiesAdapter);
        rvUsers.setItemAnimator(new DefaultItemAnimator());
        rvUsers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        rvUsers.addItemDecoration(new CityDivider(R.dimen.activity_vertical_margin));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                CityModel model = citiesAdapter.getItem(position);
                citiesListPresenter.removeCity(model);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvUsers);

        citiesAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshCities.setOnRefreshListener(this);
        swipeRefreshCities.setColorSchemeResources(R.color.primary, R.color.primary_light);
    }


    public interface CityListListener {
        void onCityClicked(final CityModel cityModel);
    }


}
