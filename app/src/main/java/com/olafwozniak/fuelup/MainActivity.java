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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";
    private RecyclerView recyclerView;
    private com.olafwozniak.fuelup.CarsListAdapter CarsListAdapter;
    private com.olafwozniak.fuelup.FuelListAdapter FuelListAdapter;
    public List<Fuels> fuelsList = FuelsLab.get(this).getFuels();
    public List<Cars> carsList = CarsLab.get(this).getCars();

    private CarsLab mCarsLab;
    private FuelsLab mFuelsLab;
    private DBHandler DbHandler;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler = new DBHandler(this);
        mCarsLab.get(this);
        mFuelsLab.get(this);


        recyclerView = findViewById(R.id.recyclerView);
        CarsListAdapter = new CarsListAdapter(this, carsList);
        FuelListAdapter = new FuelListAdapter(this, fuelsList);
        textView = findViewById(R.id.textView);
        if(carsList.isEmpty())
        {
            recyclerView.setAdapter(CarsListAdapter);
        }
        else
        {
            recyclerView.setAdapter(FuelListAdapter);                                   //default is showing fuel list in main
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initialize();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        CarsListAdapter.notifyDataSetChanged();
    }

    public void addCar(View view) {
        if(recyclerView.getAdapter() == CarsListAdapter)
        {
            Cars car = new Cars();
            DbHandler.addCar(car);
            CarsListAdapter.notifyDataSetChanged();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("position", car.getId());
            intent.putExtra("id", car.getId());
            MainActivity.this.startActivity(intent);
        }
        else if(recyclerView.getAdapter() == FuelListAdapter)
        {
            if(carsList.isEmpty())
            {
                Snackbar infocar = Snackbar.make(textView, "Add at least one car", Snackbar.LENGTH_SHORT);
                infocar.show();
            }
            else
            {
                Fuels fuels = new Fuels();
                DbHandler.addFuel(fuels);
                FuelListAdapter.notifyDataSetChanged();
                Intent intent = new Intent(MainActivity.this, FuelAddActivity.class);
                intent.putExtra("position", fuels.getIdFuel());
                intent.putExtra("id", fuels.getIdFuel());
                MainActivity.this.startActivity(intent);
            }
        }
    }

    public void goFuel(View view) {
        if(carsList.isEmpty())
        {
            Snackbar infocar = Snackbar.make(textView, "Add at least one car", Snackbar.LENGTH_SHORT);
            infocar.show();
        }
        else
        {
            recyclerView.setAdapter(FuelListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void goCar(View view) {
        recyclerView.setAdapter(CarsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initialize() {
        Cursor c = DbHandler.getCars();
        Cursor f = DbHandler.getFuels();
        carsList.clear();
        fuelsList.clear();

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
        if (f.getCount() != 0) {
            while (f.moveToNext()) {
                Fuels fuel = new Fuels();
                fuel.setIdFuel(Integer.parseInt(f.getString(1)));
                fuel.setTitleFuel(f.getString(2));
                fuel.setLiters(f.getFloat(3));
                fuel.setsPriceFuel(f.getFloat(4));
                fuel.setsTotalPrice(f.getFloat(5));
                fuel.setCarID(f.getString(6));
                fuel.setTypeFuel(f.getString(7));
                fuel.setsMileage(f.getFloat(8));
                fuel.setDate(new Date(f.getString(9)));
                fuel.setbFull(f.getInt(10) > 0);
                fuel.setfAvergeLiters(f.getFloat(11));
                fuel.setfAvergeCost(f.getFloat(12));
                fuelsList.add(fuel);
            }
        }
    }
}
