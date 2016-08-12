package ru.innopolis.innoweather.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.domain.interactor.GetWeatherDetails;
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
    private final GetWeatherDetails getWeatherDetails;
    private final CityModelDataMapper cityModelDataMapper;

    private Collection<City> citiesCollection = new ArrayList<>();
    private Collection<Weather> weatherCollection = new ArrayList<>();

    @Inject
    public CitiesListPresenter(@Named("citiesList") UseCase getCitiesUseCase,
                               @Named("weatherDetails") UseCase getWeatherDetails,
                               CityModelDataMapper cityModelDataMapper) {
        this.getCitiesUseCase = getCitiesUseCase;
        this.cityModelDataMapper = cityModelDataMapper;
        this.getWeatherDetails = (GetWeatherDetails) getWeatherDetails;
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
        showCitiesCollectionInView(cityCollection, null);
    }

    private void showCitiesCollectionInView(Collection<City> cityCollection, Collection<Weather> weatherCollection) {
        final Collection<CityModel> cityModelCollection = cityModelDataMapper.transform(cityCollection, weatherCollection);
        citiesListView.renderCitiesList(cityModelCollection);
    }

    private void getCitiesList() {
        this.getCitiesUseCase.execute(new Subscriber<City>() {
            @Override
            public void onCompleted() {
                hideProgressView();
                showCitiesCollectionInView(citiesCollection, weatherCollection);
                Log.d(TAG, "onCompleted: all cities downloaded");
            }

            @Override
            public void onError(Throwable e) {
                hideProgressView();
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(City city) {
                Log.d(TAG, "onNext: new City came");
                citiesCollection.add(city);
                getWeatherDetails.setCityId(city.getId());
                getWeatherDetails.execute(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: all weathers here");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Weather weather) {
                        weatherCollection.add(weather);
                        showCitiesCollectionInView(citiesCollection, weatherCollection);
                        Log.d(TAG, "onNext: new weather came");
                    }
                });
            }
        });
    }

    private void showTemperatureInCity(City city) {


    }

    public void addNewCity() {

    }
}