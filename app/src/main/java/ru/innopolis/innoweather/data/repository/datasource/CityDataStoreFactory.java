package ru.innopolis.innoweather.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.innopolis.innoweather.data.cache.BaseCache;
import ru.innopolis.innoweather.data.cache.Cache;
import ru.innopolis.innoweather.data.cache.FileManager;
import ru.innopolis.innoweather.data.cache.serializer.JsonSerializer;
import ru.innopolis.innoweather.data.entity.CityEntity;
import ru.innopolis.innoweather.domain.executor.ThreadExecutor;

@Singleton
public class CityDataStoreFactory {
    private static final String TAG = "CityDataStoreFactory";

    // Will be used for caching
    private final Context context;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;
    private final String prefix = "city_";

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
        Cache<CityEntity> cache = new BaseCache<>(context, serializer, fileManager, threadExecutor,CityEntity.class, prefix);
        return new LocalCityDataStore(cache);
    }
}
