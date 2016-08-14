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
package ru.innopolis.innoweather.data.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.entity.CityEntity;

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
   * @param cityEntity {@link CityEntity} to serialize.
   */
  public String serialize(Object cityEntity) {
    return gson.toJson(cityEntity, cityEntity.getClass());
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param jsonString A json string to deserialize.
   * @return {@link CityEntity}
   */
  public CityEntity deserialize(String jsonString) {
    return gson.fromJson(jsonString, CityEntity.class);
  }
}
