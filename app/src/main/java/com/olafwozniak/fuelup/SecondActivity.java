package com.olafwozniak.fuelup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    public List<Cars> cars =  CarsLab.get(this).getCars();
    public int Id;
    private DBHandler mDbHandler;

    private ViewPager2 viewPager2;
    private int carPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view_pager);
        mDbHandler = new DBHandler(this);
        viewPager2 = findViewById(R.id.detail_view_pager);
        CarActivityAdapter adapter = new CarActivityAdapter();
        viewPager2.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Id = getIntent().getIntExtra("id",0);
            carPos = getIntent().getIntExtra("position",0);
        }
        viewPager2.setCurrentItem(carPos, false);
    }

    public class CarActivityAdapter extends RecyclerView.Adapter<CarActivityAdapter.CarViewHolder> {

        public class CarViewHolder extends RecyclerView.ViewHolder {
            private EditText Title;
            private EditText licentNumber;
            private EditText Type;

            CarActivityAdapter adapter;

            public CarViewHolder(@NonNull View itemView, CarActivityAdapter adapter){
                super(itemView);
                Title = itemView.findViewById(R.id.carName);
                licentNumber = itemView.findViewById(R.id.licentNumber);
                Type = itemView.findViewById(R.id.type);
                this.adapter = adapter;
            }
        }

        @NonNull
        @Override
        public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CarViewHolder(
                    (LayoutInflater.from(SecondActivity.this).inflate(
                            R.layout.activity_second,
                            parent,
                            false
                    )),
                    this
            );
        }

        @Override
        public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
            Cars current = cars.get(position);
            //holder.viewId.setText(current.getId());
            holder.Title.setText(current.getTitle());
            holder.licentNumber.setText(current.getLicentNumber());
            holder.Type.setText(current.getType());
            Id = cars.get(position).getId();
            carPos = viewPager2.getCurrentItem();
            holder.Title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    current.setTitle(holder.Title.getText().toString());
                    mDbHandler.updateCar(current.getId(), current.getTitle(), current.getLicentNumber(), current.getType());
                }
            });
            holder.licentNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    current.setLicentNumber(holder.licentNumber.getText().toString());
                    mDbHandler.updateCar(current.getId(), current.getTitle(), current.getLicentNumber(), current.getType());
                }
            });
            holder.Type.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    current.setType(holder.Type.getText().toString());
                    mDbHandler.updateCar(current.getId(), current.getTitle(), current.getLicentNumber(), current.getType());
                }
            });
        }

        @Override
        public int getItemCount() {
            return cars.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() { super.onPause(); }

    public void delCrime(View view) {
        mDbHandler.deleteCar(Id);
        finish();
    }
}