package ru.innopolis.innoweather.presentation.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;
import ru.innopolis.innoweather.presentation.di.modules.ApplicationModule;
import ru.innopolis.innoweather.presentation.view.activity.BaseActivity;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  WeatherRepository weatherRepository();
}
