package com.olafwozniak.fuelup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    CarsLab mCarsLab;
    FuelsLab mFuelsLab;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Cars.db";

    public static final String TABLE_NAME = "car";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LICENTPLATE = "licencePlate";
    public static final String COLUMN_FUELTYPE = "gas";
    public static final String COLUMN_ACTIVE = "1";

    public static final String TABLE_NAME_FUEL = "fuel";
    public static final String COLUMN_ID_FUEL = "id";
    public static final String COLUMN_FUEL_ID = "fuel_id";
    public static final String COLUMN_TITLE_FUEL = "title";
    public static final String COLUMN_LITERS = "liters";
    public static final String COLUMN_PRICE_PER_L = "price";
    public static final String COLUMN_TOTAL_PRICE = "totalPrice";
    public static final String COLUMN_CAR = "carId";
    public static final String COLUMN_FUEL_TYPE = "fuelType";
    public static final String COLUMN_MILEAGE = "mileage";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mCarsLab = mCarsLab.get(context);
        mFuelsLab = mFuelsLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("[INFO] Initialized empty fuel database.");
        String INITIALIZE_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_ID, COLUMN_CAR_ID, COLUMN_TITLE, COLUMN_LICENTPLATE, COLUMN_FUELTYPE, COLUMN_ACTIVE);
        db.execSQL(INITIALIZE_TABLE);
        String INITIALIZE_TABLE_FUEL = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME_FUEL, COLUMN_ID_FUEL, COLUMN_FUEL_ID, COLUMN_TITLE_FUEL, COLUMN_LITERS, COLUMN_PRICE_PER_L, COLUMN_TOTAL_PRICE, COLUMN_CAR, COLUMN_FUEL_TYPE, COLUMN_MILEAGE);
        db.execSQL(INITIALIZE_TABLE_FUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(db);
    }

    public void onUpgradeFuel(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_FUEL));
        onCreate(db);
    }

    public Cursor getCars() {
        String q = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(q, null);
    }

    public Cursor getFuels() {
        String f = String.format("SELECT * FROM %s", TABLE_NAME_FUEL);
        SQLiteDatabase dbf = this.getReadableDatabase();
        return dbf.rawQuery(f, null);
    }

    public Cursor getCar(int id) {
        String uuidString = Integer.toString(id);
        String q = String.format("SELECT * FROM %s WHERE = ?", TABLE_NAME);
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(q, new String[]{uuidString});
    }

    public void addCar(Cars car) {
        mCarsLab.newCar(car);
        ContentValues v = new ContentValues();
        v.put(COLUMN_CAR_ID, mCarsLab.getCars().size());
        v.put(COLUMN_TITLE, car.getTitle());
        v.put(COLUMN_LICENTPLATE, "");
        v.put(COLUMN_FUELTYPE, "");
        v.put(COLUMN_ACTIVE,"1");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, v);
        db.close();
    }

    public void addFuel(Fuels fuel) {
        mFuelsLab.newFuel(fuel);
        ContentValues v = new ContentValues();
        v.put(COLUMN_FUEL_ID, mFuelsLab.getFuels().size());
        v.put(COLUMN_TITLE_FUEL, fuel.getTitleFuel());
        v.put(COLUMN_LITERS, "");
        v.put(COLUMN_FUEL_TYPE, "");
        v.put(COLUMN_TOTAL_PRICE, "");
        v.put(COLUMN_PRICE_PER_L, "");
        v.put(COLUMN_CAR, "");
        v.put(COLUMN_MILEAGE,"");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_FUEL, null, v);
        db.close();
    }

    public void deleteCar(int id) {
        String uuidString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
        mCarsLab.deleteCar(id);
    }

    public void deleteFuel(int id) {
        String uuidString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FUEL, COLUMN_FUEL_ID + " = ?", new String[]{String.valueOf(uuidString)});
        db.close();
        mFuelsLab.deleteFuel(id);
    }

    public void updateCar(int id, String title, String numberPlate, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(title == "") {
            cv.put(COLUMN_TITLE, "New car");
        } else {
            cv.put(COLUMN_TITLE, title);
        }
        cv.put(COLUMN_LICENTPLATE, numberPlate);
        cv.put(COLUMN_FUELTYPE, type);
        db.update(TABLE_NAME, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void updateFuel(int id, String title, String liters, String pricePerLiters, String totalPrice, String type, String mileage, int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(title == "") {
            cv.put(COLUMN_TITLE_FUEL, "New fuel up");
        } else {
            cv.put(COLUMN_TITLE_FUEL, title);
        }
        cv.put(COLUMN_LITERS, liters);
        cv.put(COLUMN_PRICE_PER_L, pricePerLiters);
        cv.put(COLUMN_FUEL_TYPE, type);
        cv.put(COLUMN_TOTAL_PRICE, totalPrice);
        cv.put(COLUMN_CAR, carId);
        cv.put(COLUMN_MILEAGE, mileage);
        db.update(TABLE_NAME, cv, COLUMN_FUEL_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void activeCar(int id, String active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACTIVE, active);
        db.update(TABLE_NAME, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}


