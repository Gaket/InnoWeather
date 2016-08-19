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
package ru.innopolis.innoweather.data.cache;

import android.content.Context;

import java.io.File;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.data.entity.HasId;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import rx.Observable;

/**
 * {@link Cache} implementation.
 */
@Singleton
public class CityCacheImpl implements Cache<CityEntity> {

    private static final String SETTINGS_FILE_NAME = "ru.innopolis.innoweather.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "city_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000; // 10 minutes

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link CityCacheImpl}.
     *
     * @param context A
     * @param cityCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public CityCacheImpl(Context context, JsonSerializer cityCacheSerializer,
                         FileManager fileManager, ThreadExecutor executor) {
        if (context == null || cityCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = cityCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<CityEntity> get(final int cityId) {
        return Observable.create(subscriber -> {
            File cityEntityFile = CityCacheImpl.this.buildFile(cityId);
            String fileContent = CityCacheImpl.this.fileManager.readFileContent(cityEntityFile);
            CityEntity cityEntity = CityCacheImpl.this.serializer.deserialize(fileContent, CityEntity.class);

            if (cityEntity != null) {
                subscriber.onNext(cityEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new RuntimeException("City not found"));
            }
        });
    }

    @Override
    public Observable<CityEntity> getAll() {
        Collection<File> cityEntityFiles = fileManager.getAllfiles(cacheDir, DEFAULT_FILE_NAME);
        return Observable.create(subscriber -> {
            for (File file : cityEntityFiles) {
                String fileContent = CityCacheImpl.this.fileManager.readFileContent(file);
                CityEntity cityEntity = CityCacheImpl.this.serializer.deserialize(fileContent, CityEntity.class);

                if (cityEntity != null) {
                    subscriber.onNext(cityEntity);
                } else {
                    subscriber.onError(new RuntimeException("City not found"));
                }
            }
            subscriber.onCompleted();
        });
    }


    @Override
    public void put(HasId entity) {
        if (entity != null) {
            File cityEntitiyFile = this.buildFile(entity.getId());
            if (!isCached(entity.getId())) {
                String jsonString = this.serializer.serialize(entity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, cityEntitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int cityId) {
        File cityEntitiyFile = this.buildFile(cityId);
        return this.fileManager.exists(cityEntitiyFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    @Override
    public boolean remove(HasId cityEntity) {
        boolean success;
        if (cityEntity != null && isCached(cityEntity.getId())) {
            File cityEntitiyFile = this.buildFile(cityEntity.getId());
            success = cityEntitiyFile.delete();
        } else {
            success = false;
        }
        return success;
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param cityId The id city to build the file.
     * @return A valid file.
     */
    private File buildFile(int cityId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(cityId);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
