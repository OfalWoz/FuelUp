package com.olafwozniak.fuelup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";
    private RecyclerView recyclerView;
    private com.olafwozniak.fuelup.CarsListAdapter CarsListAdapter;
    public List<Cars> carsList = CarsLab.get(this).getCars();

    private CarsLab mCarsLab;
    private DBHandler DbHandler;
    private SearchView svCarSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHandler = new DBHandler(this);
        mCarsLab.get(this);
        initialize();

        recyclerView = findViewById(R.id.recyclerView);
        CarsListAdapter = new CarsListAdapter(this, carsList);
        recyclerView.setAdapter(CarsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        svCarSearcher = findViewById(R.id.search);

        svCarSearcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CarsListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        svCarSearcher.setOnCloseListener(() -> {
            initialize();
            CarsListAdapter.notifyDataSetChanged();
            return false;
        });
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
    }

    private void initialize() {
        Cursor c = DbHandler.getCars();
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
