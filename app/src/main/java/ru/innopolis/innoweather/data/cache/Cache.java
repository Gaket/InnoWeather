package ru.innopolis.innoweather.data.cache;


import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.HasId;
import rx.Observable;

/**
 * An interface representing a city Cache.
 */
public interface Cache<T> {
  /**
   * Gets an {@link rx.Observable} which will emit a {@link CityEntity}.
   *
   * @param cityId The city id to retrieve data.
   */
  Observable<T> get(final int cityId);

  /**
   * Gets an {@link rx.Observable} which will emit all {@link CityEntity}.
   *
   */
  Observable<T> getAll();

  /**
   * Puts and element into the cache.
   *
   * @param entity Element to insert in the cache.
   */
  void put(HasId entity);

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

  /**
   * Remove given entity
   *
   * @param entity to remove
   */
  boolean remove(HasId entity);

}
