package com.olafwozniak.fuelup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FuelListAdapter extends RecyclerView.Adapter<FuelListAdapter.FuelViewHolder> {

    public final List<Fuels> lFuel;
    public LayoutInflater inflater;


    public FuelListAdapter(Context context, List<Fuels> fuellist){
        this.inflater = LayoutInflater.from(context);
        this.lFuel = fuellist;
    }

    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";


    class FuelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView id;
        public TextView fuelName;
        private TextView liters;
        private TextView type;
        private TextView pricePerL;
        private TextView totalPrice;
        private TextView mileage;
        private TextView date;
        private TextView averageLiters;
        private TextView averageCost;
        private int CarId;
        public Button editFuel;
        final FuelListAdapter adapter;
        public final Context context;

        public FuelViewHolder(@NonNull View itemView, FuelListAdapter adapter) {
            super(itemView);
            context = itemView.getContext();
            id = itemView.findViewById(R.id.id);
            liters = itemView.findViewById(R.id.liters);
            totalPrice = itemView.findViewById(R.id.total_price);
            pricePerL = itemView.findViewById(R.id.price_per_l);
            mileage = itemView.findViewById(R.id.mileage);
            type = itemView.findViewById(R.id.type_main);
            date = itemView.findViewById(R.id.date);
            averageLiters = itemView.findViewById(R.id.averageLiters);
            averageCost = itemView.findViewById(R.id.averageCost);
            itemView.setOnClickListener(this);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Fuels currentFuel = lFuel.get(position);
            Intent intent = new Intent(context, FuelAddActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("id", currentFuel.getIdFuel());
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public FuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.fuel_list_item, parent, false);
        return new FuelViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FuelViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Fuels currentFuel = lFuel.get(position);
        holder.id.setText("#"+(currentFuel.getIdFuel()));
        holder.liters.setText(String.format("%.2f",currentFuel.getLiters())+" l");
        holder.totalPrice.setText(String.format("%.2f",currentFuel.getsTotalPrice())+" pln");
        holder.pricePerL.setText(String.format("%.2f",currentFuel.getsPriceFuel())+" pln/l");
        holder.mileage.setText(String.format("%.0f",currentFuel.getiMileage())+" km");
        holder.date.setText(dateFormat.format(currentFuel.getDate()));
        if(currentFuel.getIdFuel() == 1 ) {
            holder.averageLiters.setText("First refueling.");
            holder.averageCost.setText("");
        }
        else if(!(currentFuel.getbFull())) {
            holder.averageLiters.setText("Partial refueling.");
            holder.averageCost.setText("");
        } else {
            holder.averageLiters.setText(String.format("%.2f", currentFuel.getfAvergeLiters()) + " l/100km");
            holder.averageCost.setText(String.format("%.2f", currentFuel.getfAvergeCost()) + " pln/1km");
        }
    }

    @Override
    public int getItemCount() {
        return lFuel.size();
    }


}
