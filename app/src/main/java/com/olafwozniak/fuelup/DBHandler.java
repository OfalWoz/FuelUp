package com.olafwozniak.fuelup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {

    CarsLab mCarsLab;
    FuelsLab mFuelsLab;

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "car.db";

    public static final String TABLE_NAME_CAR = "cars";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LICENTPLATE = "licencePlate";
    public static final String COLUMN_FUELTYPE = "fuelTypeCar";
    public static final String COLUMN_ACTIVE = "active";

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
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_FULL = "full";
    public static final String COLUMN_AVERAGE_LITERS = "averageLiters";
    public static final String COLUMN_AVERAGE_COST = "averageCost";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mFuelsLab = mFuelsLab.get(context);
        mCarsLab = mCarsLab.get(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("[INFO] Initialized empty fuel database.");
        String INITIALIZE_TABLE_CAR = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME_CAR, COLUMN_ID, COLUMN_CAR_ID, COLUMN_TITLE, COLUMN_LICENTPLATE, COLUMN_FUELTYPE, COLUMN_ACTIVE);
        String INITIALIZE_TABLE_FUEL = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME_FUEL, COLUMN_ID_FUEL, COLUMN_FUEL_ID, COLUMN_TITLE_FUEL, COLUMN_LITERS, COLUMN_PRICE_PER_L, COLUMN_TOTAL_PRICE, COLUMN_CAR, COLUMN_FUEL_TYPE, COLUMN_MILEAGE, COLUMN_DATE, COLUMN_FULL, COLUMN_AVERAGE_LITERS, COLUMN_AVERAGE_COST);
        db.execSQL(INITIALIZE_TABLE_FUEL);
        db.execSQL(INITIALIZE_TABLE_CAR);
        //db.execSQL("CREATE TABLE "+TABLE_NAME_CAR+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY,"+COLUMN_CAR_ID+" TEXT, "+COLUMN_TITLE+" TEXT, "+COLUMN_LICENTPLATE+" TEXT, "+COLUMN_FUELTYPE+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CAR);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_FUEL);
        onCreate(db);
    }

    public void onUpgradeFuel(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_FUEL));
        onCreate(db);
    }

    public Cursor getCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME_CAR, null);
    }

    public Cursor getFuels() {
        String f = String.format("SELECT * FROM %s", TABLE_NAME_FUEL);
        SQLiteDatabase dbf = this.getReadableDatabase();
        return dbf.rawQuery(f, null);
    }

    public void addCar(Cars car) {
        ContentValues v = new ContentValues();
        v.put(COLUMN_CAR_ID, mCarsLab.getCars().size());
        if(car.getTitle() == "")
        {
            v.put(COLUMN_TITLE, "New Car #" + (int)(car.getId()));
        }
        else
        {
            v.put(COLUMN_TITLE, car.getTitle());
        }
        v.put(COLUMN_LICENTPLATE, car.getLicentNumber());
        v.put(COLUMN_FUELTYPE, car.getType());
        v.put(COLUMN_ACTIVE,"yes");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_CAR, null, v);
        db.close();
        mCarsLab.newCar(car);
        //activeCar(car.getId(), "yes");
    }

    public void addFuel(Fuels fuel) {
        mFuelsLab.newFuel(fuel);
        ContentValues v = new ContentValues();
        v.put(COLUMN_FUEL_ID, mFuelsLab.getFuels().size());
        v.put(COLUMN_TITLE_FUEL, fuel.getTitleFuel());
        v.put(COLUMN_LITERS, fuel.getLiters());
        v.put(COLUMN_FUEL_TYPE, fuel.getTypeFuel());
        v.put(COLUMN_TOTAL_PRICE, fuel.getsTotalPrice());
        v.put(COLUMN_PRICE_PER_L, fuel.getsPriceFuel());
        v.put(COLUMN_CAR, fuel.getCarID());
        v.put(COLUMN_MILEAGE,fuel.getiMileage());
        v.put(COLUMN_DATE, fuel.getDate().toString());
        v.put(COLUMN_FULL, fuel.getbFull());
        v.put(COLUMN_AVERAGE_LITERS, fuel.getfAvergeLiters());
        v.put(COLUMN_AVERAGE_COST, fuel.getfAvergeCost());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME_FUEL, null, v);
        db.close();
    }

    public void deleteCar(int id) {
        String uuidString = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CAR, COLUMN_CAR_ID + " = ?", new String[]{String.valueOf(uuidString)});
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

    public void updateCar(int id, String title, String numberPlate, String type, String active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(title == "") {
            cv.put(COLUMN_TITLE, "New car");
        } else {
            cv.put(COLUMN_TITLE, title);
        }
        cv.put(COLUMN_LICENTPLATE, numberPlate);
        cv.put(COLUMN_FUELTYPE, type);
        cv.put(COLUMN_ACTIVE, "yes");
        db.update(TABLE_NAME_CAR, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
        //activeCar(id, "yes");
    }

    public void updateFuel(int id, String title, String liters, String pricePerLiters, String totalPrice, String type, String mileage, String carId, Date date, Boolean full, Float AverageLiters, Float AverageCost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(title == "")
        {
            cv.put(COLUMN_TITLE_FUEL, "New fuel up");
        }
        else
        {
            cv.put(COLUMN_TITLE_FUEL, title);
        }
        cv.put(COLUMN_LITERS, liters);
        cv.put(COLUMN_PRICE_PER_L, pricePerLiters);
        cv.put(COLUMN_FUEL_TYPE, type);
        cv.put(COLUMN_TOTAL_PRICE, totalPrice);
        cv.put(COLUMN_CAR, carId);
        cv.put(COLUMN_MILEAGE, mileage);
        cv.put(COLUMN_DATE, date.toString());
        cv.put(COLUMN_FULL, full);
        cv.put(COLUMN_AVERAGE_LITERS, AverageLiters);
        cv.put(COLUMN_AVERAGE_COST, AverageCost);
        db.update(TABLE_NAME_FUEL, cv, COLUMN_FUEL_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void activeCar(int id, String active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_ACTIVE, "no");
        db.update(TABLE_NAME_CAR, cv,COLUMN_ACTIVE + " = ?",new String[]{active});
        cv.put(COLUMN_ACTIVE, active);
        db.update(TABLE_NAME_CAR, cv, COLUMN_CAR_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}


