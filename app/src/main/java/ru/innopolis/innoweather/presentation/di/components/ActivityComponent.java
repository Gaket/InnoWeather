package ru.innopolis.innoweather.presentation.di.components;

import android.app.Activity;
import dagger.Component;
import ru.innopolis.innoweather.presentation.di.PerActivity;
import ru.innopolis.innoweather.presentation.di.modules.ActivityModule;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
