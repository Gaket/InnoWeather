package ru.innopolis.innoweather.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.CityCacheImpl;
import ru.innopolis.innoweather.data.cache.FileManager;
import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;

@Singleton
public class CityDataStoreFactory {
    private static final String TAG = "CityDataStoreFactory";

    // Will be used for caching
    private final Context context;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    @Inject
    public CityDataStoreFactory(Context context, JsonSerializer serializer, FileManager fileManager, ThreadExecutor threadExecutor) {
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = threadExecutor;
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    public CityDataStore create() {
        CityDataStore cityDataStore;
        cityDataStore = createLocalDataStore();
        return cityDataStore;
    }

    public CityDataStore createLocalDataStore() {
        return new LocalCityDataStore(new CityCacheImpl(context, serializer, fileManager, threadExecutor));
    }
}
