package ru.innopolis.innoweather.presentation.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.innopolis.innoweather.R;

public class CityPickerDialogFragment extends DialogFragment {
    private static final String TAG = "CityPickerDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(R.layout.city_picker_dialog);
        builder.setTitle(getString(R.string.dialog_title_pick_city));
        builder.setNegativeButton(getString(R.string.dialog_cancel), (DialogInterface dialog, int which) -> {dialog.dismiss();});
        builder.setPositiveButton(getString(R.string.dialog_add_city), (DialogInterface dialog, int which) -> {dialog.dismiss();});

        // Can't use try-with-resources because MinSDK = 16
        List<String> cities = getCitiesList("cities.txt");
        return builder.create();
    }

    private List<String> getCitiesList(String fileName) {
        List<String> cities = Collections.emptyList();
        try {
            AssetManager am = getActivity().getAssets();
            InputStream fio = am.open(fileName);
            InputStreamReader reader = new InputStreamReader(fio);
            BufferedReader br = new BufferedReader(reader);
            String line;
            cities = new ArrayList<>(2000); // 2000 is an approximate number of cities
            while ((line = br.readLine()) != null) {
                cities.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "onCreateDialog: Can't read cities", e);
        }
        return cities;
    }

}
