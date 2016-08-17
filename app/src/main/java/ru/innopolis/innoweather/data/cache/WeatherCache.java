/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.innopolis.innoweather.data.cache;


import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import rx.Observable;

/**
 * An interface representing a city Cache.
 */
public interface WeatherCache {
  /**
   * Gets an {@link Observable} which will emit a {@link WeatherEntity}.
   *
   * @param cityId The city id to retrieve data.
   */
  Observable<WeatherEntity> getWeather(final int cityId);

  /**
   * Gets an {@link Observable} which will emit all {@link CityEntity}.
   *
   */
  Observable<WeatherEntity> getAll();

  /**
   * Puts an element into the cache.
   *
   * @param weatherEntity Element to insert in the cache.
   */
  void put(WeatherEntity weatherEntity);

  /**
   * Checks if an element (City) exists in the cache.
   *
   * @param cityId The id used to look for inside the cache.
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached(final int cityId);

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}