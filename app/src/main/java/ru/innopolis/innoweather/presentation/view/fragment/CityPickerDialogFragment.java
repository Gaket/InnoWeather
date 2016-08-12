package ru.innopolis.innoweather.presentation.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.innopolis.innoweather.R;

public class CityPickerDialogFragment extends DialogFragment {
    private static final String TAG = "CityPickerDialog";

    final int CITIES_NUMBER = 9000; // approximate number of cities
    final String fileName = "cities.txt";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_city_picker, null);
        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.actv_cities_picker);

        // Can't use try-with-resources because MinSDK = 16
        List<String> cities = getCitiesList(fileName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, cities);
        textView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle(getString(R.string.dialog_title_pick_city));
        builder.setNegativeButton(getString(R.string.dialog_cancel), (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(getString(R.string.dialog_add_city), (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }


    private List<String> getCitiesList(String fileName) {
        List<String> cities = Collections.emptyList();
        try {
            AssetManager am = getActivity().getAssets();
            InputStream fio = am.open(fileName);
            InputStreamReader reader = new InputStreamReader(fio, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            String line;
            cities = new ArrayList<>(CITIES_NUMBER);
            while ((line = br.readLine()) != null) {
                cities.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "onCreateDialog: Can't read cities", e);
        }
        return cities;
    }

}
