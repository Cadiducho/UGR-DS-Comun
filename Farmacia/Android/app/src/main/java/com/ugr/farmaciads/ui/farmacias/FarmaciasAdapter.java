package com.ugr.farmaciads.ui.farmacias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ugr.farmaciads.R;

import java.util.ArrayList;
import java.util.List;

import p3.farmacia.modelo.Farmacia;

public class FarmaciasAdapter extends RecyclerView.Adapter<FarmaciasAdapter.FarmaciaViewHolder> implements Filterable {
    private Context context;
    private List<Farmacia> farmaciasList;
    private List<Farmacia> farmaciasListFiltered;
    private FarmaciasAdapterListener listener;

    public class FarmaciaViewHolder extends RecyclerView.ViewHolder {
        public TextView name, id, longitude, latitude;

        public FarmaciaViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.farmacia_name);
            id = view.findViewById(R.id.farmacia_id);
            latitude = view.findViewById(R.id.farmacia_latitud);
            longitude = view.findViewById(R.id.farmacia_longitud);

            view.setOnClickListener(view1 -> {
                // send selected contact in callback
                listener.onFarmaciaSelected(farmaciasListFiltered.get(getAdapterPosition()));
            });
        }
    }

    public FarmaciasAdapter(Context context, List<Farmacia> farmaciasList, FarmaciasAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.farmaciasList = farmaciasList;
        this.farmaciasListFiltered = farmaciasList;
    }

    @Override
    public FarmaciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farmacia_row_item, parent, false);

        return new FarmaciaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FarmaciaViewHolder holder, final int position) {
        final Farmacia farmacia = farmaciasListFiltered.get(position);

        holder.name.setText(farmacia.getNombre());
        holder.id.setText(String.format("#%s", farmacia.getId()));
        holder.longitude.setText(String.format("Longitud: %s", farmacia.getLongitud()));
        holder.latitude.setText(String.format("Latitud: %s", farmacia.getLatitud()));

    }

    @Override
    public int getItemCount() {
        return farmaciasListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    farmaciasListFiltered = farmaciasList;
                } else {
                    List<Farmacia> filteredList = new ArrayList<>();
                    for (Farmacia row : farmaciasList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNombre().toLowerCase().contains(charString.toLowerCase()) || String.valueOf(row.getId()).contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    farmaciasListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = farmaciasListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                farmaciasListFiltered = (ArrayList<Farmacia>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface FarmaciasAdapterListener {
        void onFarmaciaSelected(Farmacia farmacia);
    }
}
