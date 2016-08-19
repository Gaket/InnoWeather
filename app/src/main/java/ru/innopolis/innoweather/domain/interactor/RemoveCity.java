package ru.innopolis.innoweather.domain.interactor;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.City;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.CityRepository;
import rx.Observable;


public class RemoveCity extends UseCase {
    private static final String TAG = "RemoveCity";

    private City city;

    private final CityRepository cityRepository;

    @Inject
    public RemoveCity(CityRepository cityRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.cityRepository = cityRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return cityRepository.removeCity(city);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
