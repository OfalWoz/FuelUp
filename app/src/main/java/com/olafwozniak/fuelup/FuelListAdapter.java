package com.olafwozniak.fuelup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
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
            itemView.setOnClickListener(this);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
           //int position = getLayoutPosition();
           //Fuels currentFuel = lFuel.get(position);
           //Intent intent = new Intent(context, SecondActivity.class);
           //intent.putExtra("position", position);
           //intent.putExtra("id", currentFuel.getId());
           //context.startActivity(intent);
        }

        public void editFuel(View v){
            int position = getLayoutPosition();
            Fuels currentFuel = lFuel.get(position);
            Intent intent = new Intent(context, SecondActivity.class);
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
        Fuels currentFuel = lFuel.get(position);
        holder.id.setText("#"+(currentFuel.getIdFuel()+1));
        holder.liters.setText(currentFuel.getLiters());
        holder.totalPrice.setText(currentFuel.getsTotalPrice()+"pln");
        holder.pricePerL.setText(currentFuel.getsPriceFuel()+"pln/l");
        holder.mileage.setText(currentFuel.getiMileage());
        holder.CarId = currentFuel.getCarID();
    }

    @Override
    public int getItemCount() {
        return lFuel.size();
    }


}
