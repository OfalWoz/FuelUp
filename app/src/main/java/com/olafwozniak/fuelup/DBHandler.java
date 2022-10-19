package com.olafwozniak.fuelup;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    CarsLab mCarsLab;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Cars.db";

    public static final String TABLE_NAME = "car";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRICELITERS = "priceliters";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_LITERS = "liters";
    public static final String COLUMN_KM = "km";
    public static final String COLUMN_LPERKM = "lkm";
    public static final String COLUMN_COSTPERKM = "zlkm";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCarsLab = FuelLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("[INFO] Initialized empty fuel database.");
        //String INITIALIZE_TABLE = String.format("DROP DATABASE FuelDB");
        String INITIALIZE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s FLOAT, %s FLOAT, %s FLOAT, %s FLOAT, %s FLOAT, %s FLOAT)", TABLE_NAME, COLUMN_ID, COLUMN_CAR_ID, COLUMN_TITLE, COLUMN_DATE, COLUMN_PRICELITERS, COLUMN_COST, COLUMN_LITERS, COLUMN_KM, COLUMN_LPERKM, COLUMN_COSTPERKM);
        db.execSQL(INITIALIZE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public Cursor getFuels() {
        String q = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public Cursor getFuels(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE = ?", TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(q, new String[]{uuidString});
    }

    public void addFuel(Fuel fuel) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_CAR_ID, FuelLab.mFuels.size());
        v.put(COLUMN_TITLE, "Other");
        v.put(COLUMN_DATE, new Date().toString());
        v.put(COLUMN_PRICELITERS, 0);
        v.put(COLUMN_COST, 0);
        v.put(COLUMN_LITERS, 0);
        v.put(COLUMN_KM, 0);
        v.put(COLUMN_LPERKM, 0);
        v.put(COLUMN_COSTPERKM, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, v);
        db.close();
        mCarsLab.newFuel(fuel);
    }

    public void deleteFuel(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_CAR_ID, uuidString);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
        mCarsLab.deleteFuel(id);
    }

    public void updateFuel(int id, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);

        db.update(TABLE_NAME, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}


