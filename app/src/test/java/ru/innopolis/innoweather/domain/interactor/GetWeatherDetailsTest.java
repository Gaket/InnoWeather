package ru.innopolis.innoweather.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.innopolis.innoweather.data.entity.UnitsEntity;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetWeatherDetailsTest {

    private static final int FAKE_CITY_ID = 1;

    private GetWeatherDetails getWeatherDetails;

    @Mock private WeatherRepository mockWeatherRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getWeatherDetails = new GetWeatherDetails(FAKE_CITY_ID, UnitsEntity.metric, mockWeatherRepository,
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {
        getWeatherDetails.buildUseCaseObservable();

        verify(mockWeatherRepository).weather(FAKE_CITY_ID, UnitsEntity.metric.name());
        verifyNoMoreInteractions(mockWeatherRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

}