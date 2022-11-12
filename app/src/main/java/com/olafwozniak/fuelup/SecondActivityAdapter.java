package com.olafwozniak.fuelup;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class SecondActivityAdapter extends RecyclerView.Adapter<SecondActivityAdapter.CarViewHolder> {

    private List<Cars> carsList;
    private LayoutInflater inflater;

    public SecondActivityAdapter(SecondActivity context) {
        inflater = LayoutInflater.from(context);
        this.carsList = carsList;
    }

    class CarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Title;
        private TextView licentNumber;

        final SecondActivityAdapter adapter;

        public CarViewHolder(@NonNull View itemView, SecondActivityAdapter adapter) {
            super(itemView);
            Title = itemView.findViewById(R.id.carName);
            licentNumber = itemView.findViewById(R.id.licentNumber);
            this.adapter = adapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;

            int position = getLayoutPosition();
            Cars element = carsList.get(position);
            carsList.set(position, element);
            adapter.notifyItemChanged(position);

            intent = new Intent(view.getContext(), Cars.class);
            intent.putExtra("position", getLayoutPosition());
            view.getContext().startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Cars current = carsList.get(position);
        holder.Title.setText(current.getTitle());
        holder.licentNumber.setText(current.getLicentNumber());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }


}

