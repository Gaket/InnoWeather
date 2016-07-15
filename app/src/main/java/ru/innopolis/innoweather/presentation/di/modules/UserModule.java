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
package ru.innopolis.innoweather.presentation.di.modules;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.innopolis.innoweather.domain.executor.PostExecutionThread;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import ru.innopolis.innoweather.domain.interactor.GetCitiesList;
import ru.innopolis.innoweather.domain.interactor.GetWeatherDetails;
import ru.innopolis.innoweather.domain.interactor.UseCase;
import ru.innopolis.innoweather.domain.repository.CityRepository;
import ru.innopolis.innoweather.domain.repository.WeatherRepository;
import ru.innopolis.innoweather.presentation.di.PerActivity;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

    // TODO: remove hardcoded values
    private int cityId = 2172797;
    private String units = "metric";

    public UserModule() {
    }

    public UserModule(int cityId) {
        this.cityId = cityId;
    }


    @Provides
    @PerActivity
    @Named("weatherDetails")
    UseCase provideGetWeatherDetailsUseCase(
            WeatherRepository weatherRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetWeatherDetails(cityId, units, weatherRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("citiesList")
    UseCase provideGetCitiesListUseCase(
            CityRepository citiesRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetCitiesList(citiesRepository, threadExecutor, postExecutionThread);
    }
}