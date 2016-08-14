package ru.innopolis.innoweather.presentation.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class Settings {
    private static final String TAG = "Settings";
    public static final String NOT_FIRST_START = "not_first_start";
    public static final String FIRST_START = "first_start";

    Context context;

    @Inject
    public Settings(Context context) {
        this.context = context;
    }

    public boolean isFirstStart(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(NOT_FIRST_START)) {
            return false;
        } else {
            prefs.edit()
                    .putString(NOT_FIRST_START, null)
                    .apply();
            return true;
        }
    }




}
