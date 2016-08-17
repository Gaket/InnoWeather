package ru.innopolis.innoweather.data.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.domain.Weather;

/**
 * Class user as Serializer/Deserializer for user entities.
 */
@Singleton
public class JsonSerializer {

  private final Gson gson = new Gson();

  @Inject
  public JsonSerializer() {}

  /**
   * Serialize an object to Json.
   *
   * @param entity to serialize.
   */
  public <T> String serialize(T entity) {
    return gson.toJson(entity, entity.getClass());
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param jsonString A json string to deserialize.
   * @return entity}
   */
  public <T> T deserialize(String jsonString, Class<T> classOfT) {
    return gson.fromJson(jsonString, classOfT);
  }
}
