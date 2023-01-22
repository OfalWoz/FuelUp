package com.olafwozniak.fuelup;

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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FuelAddActivity extends AppCompatActivity {

    public List<Fuels> fuels =  FuelsLab.get(this).getFuels();
    public int Id;
    public String date = "Set Date";
    public String time = "Set Time";
    public Date newDate;
    private Calendar cal = Calendar.getInstance();

    private DBHandler mDbHandler;

    private ViewPager2 viewPager2;
    private int fuelPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_view_pager);
        mDbHandler = new DBHandler(this);
        viewPager2 = findViewById(R.id.detail_view_pager);
        FuelAddActivityAdapter adapter = new FuelAddActivityAdapter();
        viewPager2.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Id = getIntent().getIntExtra("id",0);
            fuelPos = getIntent().getIntExtra("position",0);
        }
        viewPager2.setCurrentItem(fuelPos, false);
    }

    public class FuelAddActivityAdapter extends RecyclerView.Adapter<FuelAddActivityAdapter.FuelViewHolder> {

        public class FuelViewHolder extends RecyclerView.ViewHolder {
            //public Date newDate;
            private TextView Fuelupnumber;
            private EditText Mileage;
            private EditText Liters;
            private EditText CostPerLiter;
            private EditText TotalCost;

            private Button Date;
            private Button Time;
            private CheckBox Full;

            FuelAddActivityAdapter adapter;

            public FuelViewHolder(@NonNull View itemView, FuelAddActivityAdapter adapter){
                super(itemView);
                Fuelupnumber = itemView.findViewById(R.id.fuelupnumber);
                Mileage = itemView.findViewById(R.id.mileage);
                Liters = itemView.findViewById(R.id.liters);
                CostPerLiter = itemView.findViewById(R.id.price_per_l);
                TotalCost = itemView.findViewById(R.id.total_price);
                Date = itemView.findViewById(R.id.date);
                Time = itemView.findViewById(R.id.time);
                Full = itemView.findViewById(R.id.full);
                this.adapter = adapter;

                Date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();

                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                FuelAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String choseMonth = "";
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, monthOfYear);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                newDate = cal.getTime();
                                if(monthOfYear + 1 < 10)
                                {
                                    choseMonth = "0" + (monthOfYear + 1);
                                }
                                else
                                {
                                    choseMonth = "" + (monthOfYear + 1);
                                }
                                Date.setText(dayOfMonth + "/" + choseMonth + "/" + year);
                            }
                        },
                        year, month, day);
                        datePickerDialog.show();
                    }
                });

                Time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(FuelAddActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    cal.set(Calendar.MINUTE, minute);
                                    newDate = cal.getTime();
                                    Time.setText(hourOfDay + ":" + minute);
                                }
                            }
                            , hour, minute, true);
                        timePickerDialog.show();
                    }
                });

            }
        }

        @NonNull
        @Override
        public FuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FuelViewHolder(
                    (LayoutInflater.from(FuelAddActivity.this).inflate(
                            R.layout.activity_addfuel,
                            parent,
                            false
                    )),
                    this
            );
        }

        @Override
        public void onBindViewHolder(@NonNull FuelViewHolder holder, int position) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Fuels current = fuels.get(position);
            cal.setTime(current.getDate());
            holder.Fuelupnumber.setText("Fuel up #"+(int)(current.getIdFuel()));
            holder.Mileage.setText(current.getiMileage().toString());
            holder.Liters.setText(current.getLiters().toString());
            holder.CostPerLiter.setText(current.getsPriceFuel().toString());
            holder.TotalCost.setText(current.getsTotalPrice().toString());
            holder.Date.setText(dateFormat.format(current.getDate()));
            holder.Time.setText(timeFormat.format(current.getDate()));
            holder.Full.setChecked(current.getbFull());
            Id = fuels.get(position).getIdFuel();
            fuelPos = viewPager2.getCurrentItem();

            holder.Liters.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.Liters.getText().toString().isEmpty() || holder.Liters.getText().toString().endsWith(".") || holder.Liters.getText().toString().startsWith(".")) {}
                    else if(holder.CostPerLiter.getText().toString().isEmpty() && !(holder.TotalCost.getText().toString().isEmpty()) && !(holder.TotalCost.getText().toString().endsWith(".")))
                    {
                        holder.CostPerLiter.setText(String.valueOf(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.Liters.getText().toString())));
                        current.setsPriceFuel(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.Liters.getText().toString()));
                    }
                    else if(holder.TotalCost.getText().toString().isEmpty() && !(holder.CostPerLiter.getText().toString().isEmpty()) && !(holder.CostPerLiter.getText().toString().endsWith(".")))
                    {
                        holder.TotalCost.setText(String.valueOf(Float.parseFloat(holder.Liters.getText().toString()) * Float.parseFloat(holder.CostPerLiter.getText().toString())));
                        current.setsTotalPrice(Float.parseFloat(holder.Liters.getText().toString()) * Float.parseFloat(holder.CostPerLiter.getText().toString()));
                    }
                    if (holder.Liters.getText().toString().isEmpty() || holder.Liters.getText().toString().endsWith(".") || holder.Liters.getText().toString().startsWith(".")) {
                    } else {
                        countAverge(current, holder);
                        current.setLiters(Float.parseFloat(holder.Liters.getText().toString()));
                        mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                    }
                }
            });

            holder.Date.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //holder.newDate = newDate;
                    current.setDate(newDate);
                    mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                }
            });

            holder.Time.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //holder.newDate = newDate;
                    current.setDate(newDate);
                    mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                }
            });

            holder.CostPerLiter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.CostPerLiter.getText().toString().isEmpty() || holder.CostPerLiter.getText().toString().endsWith(".") || holder.CostPerLiter.getText().toString().startsWith(".")) {}
                    else if(holder.Liters.getText().toString().isEmpty() && !(holder.TotalCost.getText().toString().isEmpty()) && !(holder.TotalCost.getText().toString().endsWith(".")))
                    {
                        holder.Liters.setText(String.valueOf(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.CostPerLiter.getText().toString())));
                        current.setLiters(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.CostPerLiter.getText().toString()));
                    }
                    else if(holder.TotalCost.getText().toString().isEmpty() && !(holder.Liters.getText().toString().isEmpty()) && !(holder.Liters.getText().toString().endsWith(".")))
                    {
                        holder.TotalCost.setText(String.valueOf(Float.parseFloat(holder.Liters.getText().toString()) * Float.parseFloat(holder.CostPerLiter.getText().toString())));
                        current.setsTotalPrice(Float.parseFloat(holder.Liters.getText().toString()) * Float.parseFloat(holder.CostPerLiter.getText().toString()));
                    }
                    if (holder.CostPerLiter.getText().toString().isEmpty() || holder.TotalCost.getText().toString().isEmpty() || holder.CostPerLiter.getText().toString().endsWith(".") || holder.TotalCost.getText().toString().endsWith(".")){
                    } else {
                        countAverge(current, holder);
                        current.setsPriceFuel(Float.parseFloat(holder.CostPerLiter.getText().toString()));
                        mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                    }
                }
            });

            holder.TotalCost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (holder.TotalCost.getText().toString().isEmpty() || holder.TotalCost.getText().toString().endsWith(".") || holder.TotalCost.getText().toString().startsWith(".")) {}
                    else if(holder.CostPerLiter.getText().toString().isEmpty() && !(holder.Liters.getText().toString().isEmpty()) && !(holder.Liters.getText().toString().endsWith(".")))
                    {
                        holder.CostPerLiter.setText(String.valueOf(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.Liters.getText().toString())));
                        current.setsPriceFuel(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.Liters.getText().toString()));
                    }
                    else if(holder.Liters.getText().toString().isEmpty() && !(holder.CostPerLiter.getText().toString().isEmpty()) && !(holder.CostPerLiter.getText().toString().endsWith(".")))
                    {
                        holder.Liters.setText(String.valueOf(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.CostPerLiter.getText().toString())));
                        current.setLiters(Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.CostPerLiter.getText().toString()));
                    }
                    if (holder.TotalCost.getText().toString().isEmpty() || holder.TotalCost.getText().toString().endsWith(".")){
                    } else {
                        countAverge(current, holder);
                        current.setsTotalPrice(Float.parseFloat(holder.TotalCost.getText().toString()));
                        mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                    }
                }
            });

            holder.Mileage.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s)
                {
                    if (holder.CostPerLiter.getText().toString().isEmpty() || holder.TotalCost.getText().toString().isEmpty() || holder.CostPerLiter.getText().toString().endsWith(".") || holder.TotalCost.getText().toString().endsWith(".")|| holder.Mileage.getText().toString().isEmpty() || holder.Mileage.getText().toString().endsWith(".")){}
                    else
                    {
                        countAverge(current, holder);
                        current.setsMileage(Float.parseFloat(holder.Mileage.getText().toString()));
                        mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                    }
                }
            });

            holder.Full.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                {
                    current.setbFull(holder.Full.isChecked());
                    mDbHandler.updateFuel(current.getIdFuel(), current.getTitleFuel(), current.getLiters().toString(), current.getsPriceFuel().toString(), current.getsTotalPrice().toString(), current.getTypeFuel(), current.getiMileage().toString(), current.getCarID(), current.getDate(), current.getbFull(), current.getfAvergeLiters(), current.getfAvergeCost());
                }
            });
        }

        private void countAverge(Fuels current, @NonNull FuelViewHolder holder) {
            int id = current.getIdFuel();
            if(id == 1)
            {
                current.setfAvergeLiters(0F);
                current.setfAvergeCost(0F);
            }
            else
            {
                Fuels previous = fuels.get(id-1);
                float l = Float.parseFloat(holder.TotalCost.getText().toString()) / Float.parseFloat(holder.CostPerLiter.getText().toString());
                float road = Float.parseFloat(holder.Mileage.getText().toString()) - Float.parseFloat(previous.getiMileage().toString());
                current.setfAvergeLiters((l/road) * 100F);
                current.setfAvergeCost((Float.parseFloat(holder.TotalCost.getText().toString())/road));
            }
        }

        @Override
        public int getItemCount()
        {
            return fuels.size();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onPause() { super.onPause(); }

    public void del(View view) {
        mDbHandler.deleteFuel(Id);
        finish();
    }
}