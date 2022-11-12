package com.olafwozniak.fuelup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CarsListAdapter extends RecyclerView.Adapter<CarsListAdapter.CarViewHolder> implements Filterable {

    public final List<Cars> mCars;
    public LayoutInflater inflater;


    public CarsListAdapter(Context context, List<Cars> carlist){
        this.inflater = LayoutInflater.from(context);
        this.mCars = carlist;
    }

    public static final String EXTRA_MESSAGE = "pl.edu.uwr.pum.recyclerviewwordlistjava.MESSAGE";

    class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView id;
        public TextView carName;
        private TextView licentNumber;
        private TextView type;
        final CarsListAdapter adapter;
        public final Context context;

        public CarViewHolder(@NonNull View itemView, CarsListAdapter adapter) {
            super(itemView);
            context = itemView.getContext();
            id = itemView.findViewById(R.id.id);
            carName = itemView.findViewById(R.id.car_Name);
            licentNumber = itemView.findViewById(R.id.licent_Number);
            type = itemView.findViewById(R.id.type_main);
            itemView.setOnClickListener(this);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
           int position = getLayoutPosition();
           Cars currentCar = mCars.get(position);
           Intent intent = new Intent(context, SecondActivity.class);
           intent.putExtra("position", position);
           intent.putExtra("id", currentCar.getId());
           context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CarsListAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsListAdapter.CarViewHolder holder, int position) {
        Cars currentCar = mCars.get(position);
        //holder.id.setText("#"+String.valueOf(currentCar.getId()));
        holder.carName.setText(currentCar.getTitle());
        holder.licentNumber.setText(currentCar.getLicentNumber());
        holder.type.setText(currentCar.getType());
    }

   @Override
   public Filter getFilter() {
       return filter;
   }

   Filter filter = new Filter() {
       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
           List<Cars> filter = new ArrayList<>();
           FilterResults results = new FilterResults();

           if (constraint.toString().isEmpty()) {
               filter.addAll(mCars);
           }
           else {
               for (Cars c : mCars) {
                   if (c.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                       filter.add(c);
                   }
               }
           }
           results.values = filter;
           return results;
       }

@Override
protected void publishResults(final CharSequence constraint, FilterResults results) {
           mCars.clear();
           mCars.addAll((Collection<? extends Cars>) results.values);
           notifyDataSetChanged();
       }};

    @Override
    public int getItemCount() {
        return mCars.size();
    }
}
