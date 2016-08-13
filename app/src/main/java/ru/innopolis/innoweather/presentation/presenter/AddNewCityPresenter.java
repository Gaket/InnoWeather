package ru.innopolis.innoweather.presentation.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.FilterQueryProvider;

import javax.inject.Inject;

import ru.innopolis.innoweather.data.init.CitiesDb;
import ru.innopolis.innoweather.presentation.view.AddNewCityView;

public class AddNewCityPresenter implements Presenter {
    private static final String TAG = "AddNewCityPresenter";

    private final static int[] to = new int[]{android.R.id.text1};
    private final static String[] from = new String[]{"name"};

    private AddNewCityView addNewCityView;

    @Inject
    public AddNewCityPresenter() {
    }


    public void setView(@NonNull AddNewCityView view) {
        this.addNewCityView = view;
    }


    @Override
    public void resume() {

    }


    @Override
    public void pause() {

    }


    @Override
    public void destroy() {

    }


    public CursorAdapter createAdapter(Context context) {
        // Can't use try-with-resources because MinSDK = 16
        CitiesDb db = new CitiesDb(context);
        Cursor cities = db.getCities();
        CursorAdapter adapter =
                new SimpleCursorAdapter(context,
                        android.R.layout.simple_dropdown_item_1line,
                        cities,
                        from, to, 0);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                String partialValue = null;
                if (constraint != null) {
                    partialValue = constraint.toString();
                }
                return db.filter(partialValue);
            }
        });
        return adapter;
    }
}
