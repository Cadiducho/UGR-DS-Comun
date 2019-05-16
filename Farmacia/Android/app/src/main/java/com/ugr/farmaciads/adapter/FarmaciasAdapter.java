package com.ugr.farmaciads.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ugr.farmaciads.R;

import java.util.List;

import p3.farmacia.modelo.Farmacia;

public class FarmaciasAdapter extends RecyclerView.Adapter<FarmaciasAdapter.FarmaciaViewHolder> {

    private List<Farmacia> dataList;

    public FarmaciasAdapter(List<Farmacia> dataList) {
        this.dataList = dataList;
    }

    @Override
    public FarmaciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_farmacia, parent, false);
        return new FarmaciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FarmaciaViewHolder holder, int position) {
        holder.txtFarmName.setText(dataList.get(position).getNombre());
        holder.txtFarmLatitude.setText("" + dataList.get(position).getLatitud());
        holder.txtFarmLongitude.setText("" + dataList.get(position).getLongitud());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FarmaciaViewHolder extends RecyclerView.ViewHolder {

        TextView txtFarmName, txtFarmLatitude, txtFarmLongitude;

        FarmaciaViewHolder(View itemView) {
            super(itemView);
            txtFarmName = (TextView) itemView.findViewById(R.id.txt_farmacia_name);
            txtFarmLatitude = (TextView) itemView.findViewById(R.id.txt_farmacia_latitude);
            txtFarmLongitude = (TextView) itemView.findViewById(R.id.txt_farmacia_longitude);
        }
    }
}