package ru.innopolis.innoweather.data.cache;

import android.content.Context;

import java.io.File;
import java.util.Collection;

import javax.inject.Inject;

import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.entity.WeatherEntity;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import rx.Observable;

public class WeatherCacheImpl implements WeatherCache {
    private static final String TAG = "WeatherCacheImpl";
    private static final String SETTINGS_FILE_NAME = "ru.innopolis.innoweather.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "weather_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000; // 10 minutes

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;


    /**
     * Constructor of the class {@link CityCacheImpl}.
     *
     * @param context             A
     * @param cityCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager         {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public WeatherCacheImpl(Context context, JsonSerializer cityCacheSerializer,
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
    public Observable<WeatherEntity> getWeather(int cityId) {
        return Observable.create(subscriber -> {
            File weatherEntityFile = WeatherCacheImpl.this.buildFile(cityId);
            String fileContent = WeatherCacheImpl.this.fileManager.readFileContent(weatherEntityFile);
            WeatherEntity weatherEntity = WeatherCacheImpl.this.serializer.deserialize(fileContent, WeatherEntity.class);

            if (weatherEntity != null) {
                subscriber.onNext(weatherEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new RuntimeException("Weather not found"));
            }
        });
    }

    private File buildFile(int cityId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(cityId);

        return new File(fileNameBuilder.toString());
    }

    @Override
    public Observable<WeatherEntity> getAll() {
        Collection<File> weatherEntityFiles = fileManager.getAllfiles(cacheDir, DEFAULT_FILE_NAME);
        return Observable.create(subscriber -> {
            for (File file : weatherEntityFiles) {
                String fileContent = WeatherCacheImpl.this.fileManager.readFileContent(file);
                WeatherEntity weatherEntity = WeatherCacheImpl.this.serializer.deserialize(fileContent, WeatherEntity.class);

                if (weatherEntity != null) {
                    subscriber.onNext(weatherEntity);
                } else {
                    subscriber.onError(new RuntimeException("Weather not found"));
                }
            }
            subscriber.onCompleted();
        });
    }

    @Override
    public void put(WeatherEntity weatherEntity) {
        if (weatherEntity != null) {
            File weatherEntitiyFile = this.buildFile(weatherEntity.getCityId());
            if (!isCached(weatherEntity.getCityId())) {
                String jsonString = this.serializer.serialize(weatherEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, weatherEntitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }


    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }


    /*
    * Executes a {@link Runnable} in another Thread.
    *
    * @param runnable {@link Runnable} to execute
    */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    @Override
    public boolean isCached(int cityId) {
        File userEntitiyFile = this.buildFile(cityId);
        return this.fileManager.exists(userEntitiyFile);
    }

    @Override
    public boolean isExpired() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void evictAll() {
        throw new UnsupportedOperationException();
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
}
