package ru.innopolis.innoweather.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkChecker {
    private static final String TAG = "NetworkChecker";

    private final Context context;

    @Inject
    public NetworkChecker(Context context) {
        this.context = context;
    }

    public boolean isNetworkActive() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
