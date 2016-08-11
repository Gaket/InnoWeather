package ru.innopolis.innoweather.presentation.view.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class CitiesDialogAdapter extends ArrayAdapter<String> {
    private static final String TAG = "CitiesDialogAdapter";


    public CitiesDialogAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }
}
