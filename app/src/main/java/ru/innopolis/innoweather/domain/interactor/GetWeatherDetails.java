package ru.innopolis.innoweather.domain.interactor;

import javax.inject.Inject;

import ru.innopolis.innoweather.domain.Weather;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;
import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Weather}.
 */
public class GetWeatherDetails extends UseCase {

    private final int cityId;
    private String units;
    private final WeatherRepository weatherRepository;

    @Inject
    public GetWeatherDetails(int cityId, String units, WeatherRepository weatherRepository,
                             ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.cityId = cityId;
        this.units = units;
        this.weatherRepository = weatherRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return weatherRepository.weather(cityId, units);
    }
}
