package ru.innopolis.innoweather.data.cache;

import android.content.Context;

import java.io.File;
import java.util.Collection;

import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.entity.HasId;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;
import rx.Observable;

public class BaseCache<T> implements Cache<T> {
    private static final String TAG = "BaseCache";

    private static final String SETTINGS_FILE_NAME = "ru.innopolis.innoweather.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000; // 10 minutes


    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;
    private final Class<T> typeParameterClass;
    private final String filePrefix;

    public BaseCache(Context context, JsonSerializer serializer, FileManager fileManager, ThreadExecutor threadExecutor, Class<T> typeParameterClass, String filePrefix) {
        this.context = context;
        this.cacheDir = context.getCacheDir();;
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
        this.typeParameterClass = typeParameterClass;
        this.filePrefix = filePrefix;
    }

    @Override
    public Observable<T> get(int cityId) {
        return Observable.create(subscriber -> {
            File weatherEntityFile = BaseCache.this.buildFile(cityId);
            String fileContent = BaseCache.this.fileManager.readFileContent(weatherEntityFile);
            T entity = BaseCache.this.serializer.deserialize(fileContent, typeParameterClass);

            if (entity != null) {
                subscriber.onNext(entity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new RuntimeException("Weather not found"));
            }
        });
    }

    @Override
    public Observable<T> getAll() {
        Collection<File> entityFiles = fileManager.getAllfiles(cacheDir, filePrefix);
        return Observable.create(subscriber -> {
            for (File file : entityFiles) {
                String fileContent = BaseCache.this.fileManager.readFileContent(file);
                T entity = BaseCache.this.serializer.deserialize(fileContent, typeParameterClass);

                if (entity != null) {
                    subscriber.onNext(entity);
                } else {
                    subscriber.onError(new RuntimeException("Weather not found"));
                }
            }
            subscriber.onCompleted();
        });
    }

    @Override
    public void put(HasId entity) {
        if (entity != null) {
            File entitiyFile = this.buildFile(entity.getId());
            if (!isCached(entity.getId())) {
                String jsonString = this.serializer.serialize(entity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, entitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int cityId) {
        File userEntitiyFile = this.buildFile(cityId);
        return fileManager.exists(userEntitiyFile);
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
    public boolean remove(HasId entity) {
        boolean success;
        if (entity != null && isCached(entity.getId())) {
            File entityFile = this.buildFile(entity.getId());
            success = entityFile.delete();
        } else {
            success = false;
        }
        return success;
    }

    private File buildFile(int cityId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(filePrefix);
        fileNameBuilder.append(cityId);

        return new File(fileNameBuilder.toString());
    }

    /*
  * Executes a {@link Runnable} in another Thread.
  *
  * @param runnable {@link Runnable} to execute
  */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
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
