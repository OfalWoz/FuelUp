package com.olafwozniak.fuelup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    CarsLab mCarsLab;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Cars.db";

    public static final String TABLE_NAME = "car";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LICENTPLATE = "licencePlate";
    public static final String COLUMN_FUELTYPE = "gas";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCarsLab = mCarsLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("[INFO] Initialized empty fuel database.");
        //String INITIALIZE_TABLE = String.format("DROP DATABASE FuelDB");
        String INITIALIZE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_CAR_ID, COLUMN_TITLE, COLUMN_LICENTPLATE, COLUMN_FUELTYPE);
        db.execSQL(INITIALIZE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public Cursor getCars() {
        String q = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public Cursor getCar(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE = ?", TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(q, new String[]{uuidString});
    }

    public void addCar(Cars car) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_CAR_ID, mCarsLab.getCars().size());
        v.put(COLUMN_TITLE, "New car");
        v.put(COLUMN_LICENTPLATE, "licent plate");
        v.put(COLUMN_FUELTYPE, "fuel type");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, v);
        db.close();
        mCarsLab.newCar(car);
    }

    public void deleteCar(int id) {
        String uuidString = Integer.toString(id);
        //String q = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, COLUMN_CAR_ID, uuidString);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
        mCarsLab.deleteCar(id);
    }

    public void updateCar(int id, String title, String numberPlate, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_LICENTPLATE, numberPlate);
        cv.put(COLUMN_FUELTYPE, type);
        db.update(TABLE_NAME, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}


