package ru.innopolis.innoweather.data.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.innopolis.innoweather.BuildConfig;

public class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";

    private String API_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private final String APP_ID = "b144af92ac844de2719fc0d745153f29";

    public <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Add API key to each request as a parameter
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl updatedUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid", APP_ID)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(updatedUrl)
                    .method(original.method(), original.body());

            return chain.proceed(requestBuilder.build());
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        httpClient.addInterceptor(logging);
        if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(new StethoInterceptor());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(httpClient.build())
                .build();
        return retrofit.create(serviceClass);
    }
}
