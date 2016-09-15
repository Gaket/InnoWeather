/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.innopolis.innoweather.presentation;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import ru.innopolis.innoweather.BuildConfig;
import ru.innopolis.innoweather.presentation.di.components.ApplicationComponent;
import ru.innopolis.innoweather.presentation.di.components.DaggerApplicationComponent;
import ru.innopolis.innoweather.presentation.di.modules.ApplicationModule;
import timber.log.Timber;


/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;
    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }
        if (BuildConfig.LEAKS) {
            refWatcher = LeakCanary.install(this);
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    /**
     * Get watcher to be able to look for leaks
     *
     * @param context to get application
     * @return watcher
     */
    @Nullable
    public static RefWatcher getRefWatcher(Context context) {
        AndroidApplication application = (AndroidApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
