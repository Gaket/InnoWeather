package ru.innopolis.innoweather.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.interactor.UseCase;
import ru.innopolis.innoweather.presentation.di.PerActivity;
import ru.innopolis.innoweather.presentation.mapper.CityModelDataMapper;
import ru.innopolis.innoweather.presentation.model.CityModel;
import ru.innopolis.innoweather.presentation.view.CitiesListView;
import rx.Subscriber;

@PerActivity
public class CitiesListPresenter implements Presenter {
    private static String TAG = "CitiesListPresenter";

    private CitiesListView citiesListView;

    private final UseCase getCitiesUseCase;
    private final CityModelDataMapper cityModelDataMapper;

    @Inject
    public CitiesListPresenter(@Named("citiesList") UseCase getCitiesUseCase,
                               CityModelDataMapper cityModelDataMapper) {
        this.getCitiesUseCase = getCitiesUseCase;
        this.cityModelDataMapper = cityModelDataMapper;
    }

    public void setView(@NonNull CitiesListView view) {
        this.citiesListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        getCitiesUseCase.unsubscribe();
        citiesListView = null;
    }

    /**
     * Initializes the presenter by start retrieving cities list
     */
    public void initialize() {
        this.loadCitiesList();
    }

    /**
     * Loads cities list.
     */
    private void loadCitiesList() {
        showProgressView();
        getCitiesList();
    }

    private void showProgressView() {
        citiesListView.showProgress();
    }

    private void hideProgressView() {
        citiesListView.hideProgress();
    }

    public void onCityClicked(CityModel cityModel) {
        citiesListView.viewWeather(cityModel);
    }

    private void showCitiesCollectionInView(Collection<City> cityCollection) {
        final Collection<CityModel> cityModelCollection = cityModelDataMapper.transform(cityCollection);
        citiesListView.renderCitiesList(cityModelCollection);
    }

    private void getCitiesList() {
        this.getCitiesUseCase.execute(new Subscriber<List<City>>() {
            @Override
            public void onCompleted() {
                hideProgressView();
            }

            @Override
            public void onError(Throwable e) {
                hideProgressView();
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(List<City> cities) {
                showCitiesCollectionInView(cities);
            }
        });
    }

}
