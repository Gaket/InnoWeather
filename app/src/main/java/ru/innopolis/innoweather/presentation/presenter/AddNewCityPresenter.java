package ru.innopolis.innoweather.presentation.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.FilterQueryProvider;

import javax.inject.Inject;
import javax.inject.Named;

import ru.innopolis.innoweather.data.init.CitiesDb;
import ru.innopolis.innoweather.domain.interactor.AddNewCity;
import ru.innopolis.innoweather.domain.interactor.UseCase;
import ru.innopolis.innoweather.presentation.mapper.CityModelDataMapper;
import ru.innopolis.innoweather.presentation.model.CitiesTableModel;
import ru.innopolis.innoweather.presentation.model.CityModel;
import rx.Subscriber;

public class AddNewCityPresenter implements Presenter {
    private static final String TAG = "AddNewCityPresenter";

    private final static int[] to = new int[]{android.R.id.text1};
    private final static String[] from = new String[]{"name"};

    private final AddNewCity addNewCityUc;
    private final CityModelDataMapper cityModelDataMapper;


    @Inject
    public AddNewCityPresenter(@Named("addNewCity") UseCase addNewCityUc, CityModelDataMapper cityModelDataMapper) {
        this.cityModelDataMapper = cityModelDataMapper;
        this.addNewCityUc = (AddNewCity) addNewCityUc;
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

    public void addCity(CityModel cityModel) {
        addNewCityUc.setCity(cityModelDataMapper.transform(cityModel));
        addNewCityUc.execute(new Subscriber() {
            @Override
            public void onCompleted() {

                Log.d(TAG, "onCompleted: city was successfully saved");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: problem with saving new city", e);
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    @NonNull
    public CityModel getCityModelFromCursor(Cursor city) {
        CityModel cityModel = new CityModel(city.getInt(CitiesTableModel.id.ordinal()));
        cityModel.setName(city.getString(CitiesTableModel.name.ordinal()));
        cityModel.setCountry("RU");
        return cityModel;
    }
}
