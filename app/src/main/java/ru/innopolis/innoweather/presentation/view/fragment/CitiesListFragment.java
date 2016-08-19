package ru.innopolis.innoweather.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

/**
 * A fragment representing a list of cities.
 */
public class CitiesListFragment extends BaseFragment implements CitiesListView {

    @Inject
    CitiesListPresenter citiesListPresenter;
    @Inject
    CitiesAdapter citiesAdapter;
    @BindView(R.id.rv_cities)
    RecyclerView rvUsers;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
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

    private void loadCitiesList() {
        citiesListPresenter.initialize();
    }

    private void setupRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvUsers.setLayoutManager(llm);
        rvUsers.setHasFixedSize(true);
        rvUsers.setAdapter(citiesAdapter);
        rvUsers.setItemAnimator(new DefaultItemAnimator());

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
        rlProgress.setVisibility(View.VISIBLE);
        getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
        getActivity().setProgressBarIndeterminateVisibility(false);

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public boolean update() {
        citiesListPresenter.update();
        return true;
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
    public interface CityListListener {
        void onCityClicked(final CityModel cityModel);
    }


}
