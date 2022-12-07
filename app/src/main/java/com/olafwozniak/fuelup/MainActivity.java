package com.olafwozniak.fuelup;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private com.olafwozniak.fuelup.CarsListAdapter CarsListAdapter;
    private com.olafwozniak.fuelup.FuelListAdapter FuelListAdapter;
    public List<Fuels> fuelsList = FuelsLab.get(this).getFuels();
    public List<Cars> carsList = CarsLab.get(this).getCars();

    private CarsLab mCarsLab;
    private FuelsLab mFuelsLab;
    private DBHandler DbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler = new DBHandler(this);
        mCarsLab.get(this);
        mFuelsLab.get(this);
        initialize();

        recyclerView = findViewById(R.id.recyclerView);
        CarsListAdapter = new CarsListAdapter(this, carsList);
        FuelListAdapter = new FuelListAdapter(this, fuelsList);
        recyclerView.setAdapter(FuelListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        CarsListAdapter.notifyDataSetChanged();
    }

    public void addCar(View view) {
        Cars car = new Cars();
        DbHandler.addCar(car);
        CarsListAdapter.notifyDataSetChanged();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("position", car.getId());
        intent.putExtra("id", car.getId());
        MainActivity.this.startActivity(intent);
    }

    public void goFuel(View view) {

    }

    public void goCar(View view) {

    }

    private void initialize() {
        Cursor c = DbHandler.getCars();
        Cursor f = DBHandler.getFuels();
        fuelsList.clear();
        carsList.clear();

        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                Cars car = new Cars();
                car.setId(Integer.parseInt(c.getString(1)));
                car.setTitle(c.getString(2));
                car.setLicentNumber(c.getString(3));
                car.setType(c.getString(4));
                carsList.add(car);
            }
        }
    }
}
