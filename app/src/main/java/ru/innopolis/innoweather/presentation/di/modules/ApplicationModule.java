package ru.innopolis.innoweather.presentation.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.innopolis.innoweather.data.executor.JobExecutor;
import ru.innopolis.innoweather.data.repository.WeatherDataRepository;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;
import ru.innopolis.innoweather.presentation.AndroidApplication;
import ru.innopolis.innoweather.presentation.UIThread;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  WeatherRepository provideWeatherRepository(WeatherDataRepository weatherDataRepository) {
    return weatherDataRepository;
  }
}
