package com.olafwozniak.fuelup;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FuelAddActivityAdapter extends RecyclerView.Adapter<FuelAddActivityAdapter.FuelViewHolder> {

    private List<Fuels> fuelList;
    private LayoutInflater inflater;

    public FuelAddActivityAdapter(FuelAddActivity context) {
        inflater = LayoutInflater.from(context);
        this.fuelList = fuelList;
    }

    class FuelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Fuelupnumber;
        private TextView Mileage;

        final FuelAddActivityAdapter adapter;

        public FuelViewHolder(@NonNull View itemView, FuelAddActivityAdapter adapter) {
            super(itemView);
            Fuelupnumber = itemView.findViewById(R.id.fuelupnumber);
            Mileage = itemView.findViewById(R.id.mileage);
            this.adapter = adapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;

            int position = getLayoutPosition();
            Fuels element = fuelList.get(position);
            fuelList.set(position, element);
            adapter.notifyItemChanged(position);

            intent = new Intent(view.getContext(), Fuels.class);
            intent.putExtra("position", getLayoutPosition());
            view.getContext().startActivity(intent);
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
        Fuels current = fuelList.get(position);
        holder.Fuelupnumber.setText(current.getTitleFuel());
        holder.Mileage.setText(current.getiMileage().toString());
    }

    @Override
    public int getItemCount() {
        return fuelList.size();
    }
}