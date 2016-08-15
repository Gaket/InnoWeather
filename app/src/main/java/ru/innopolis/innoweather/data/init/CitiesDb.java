package ru.innopolis.innoweather.data.init;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class CitiesDb extends SQLiteAssetHelper {
    private static final String TAG = "CitiesDb";
    private static final String DATABASE_NAME = "cities.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mReadableDb;

    public CitiesDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mReadableDb = getReadableDatabase();
    }

    public Cursor getCities() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"0 _id", "name", "country"};
        String sqlTables = "cities";
        String orderBy = "name";

        qb.setTables(sqlTables);
        Cursor c = qb.query(mReadableDb, sqlSelect, null, null,
                null, null, orderBy);

        c.moveToFirst();
        return c;
    }

    public Cursor filter(String partialValue) {
        return mReadableDb.rawQuery("SELECT _id, name FROM cities " +
                "WHERE name LIKE '" + partialValue + "%' " +
                "ORDER BY name", null);

    }

}
