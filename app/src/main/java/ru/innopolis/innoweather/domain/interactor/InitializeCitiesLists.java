package ru.innopolis.innoweather.domain.interactor;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.CityRepository;
import rx.Observable;

public class InitializeCitiesLists extends UseCase {
    private static final String TAG = "InitializeCitiesLists";

    private final CityRepository cityRepository;

    @Inject
    public InitializeCitiesLists(CityRepository cityRepository,
                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.cityRepository = cityRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return cityRepository.initializeCities();
    }
}
