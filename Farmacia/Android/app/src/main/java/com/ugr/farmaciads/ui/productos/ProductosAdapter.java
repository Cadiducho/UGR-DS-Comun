package com.ugr.farmaciads.ui.productos;

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

import p3.farmacia.modelo.Producto;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> implements Filterable {
    private Context context;
    private List<Producto> productoList;
    private List<Producto> productoListFiltered;
    private ProductosAdapterListener listener;

    public class ProductosViewHolder extends RecyclerView.ViewHolder {
        public TextView name, id, cantidad, precio;

        public ProductosViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.producto_name);
            id = view.findViewById(R.id.producto_id);
            cantidad = view.findViewById(R.id.producto_cantidad);
            precio = view.findViewById(R.id.producto_precio);

            view.setOnClickListener(view1 -> {
                // send selected contact in callback
                listener.onProductoSelected(productoListFiltered.get(getAdapterPosition()));
            });
        }
    }


    public ProductosAdapter(Context context, List<Producto> productoList, ProductosAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.productoList = productoList;
        this.productoListFiltered = productoList;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.producto_row_item, parent, false);

        return new ProductosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, final int position) {
        final Producto producto = productoListFiltered.get(position);

        holder.name.setText(producto.getNombre());
        holder.id.setText(String.format("#%s", producto.getId()));
        holder.cantidad.setText(String.format("Cantidad: %s", producto.getCantidad()));
        holder.precio.setText(String.format("Precio: %s€", producto.getPrecio()));

    }

    @Override
    public int getItemCount() {
        return productoListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productoListFiltered = productoList;
                } else {
                    List<Producto> filteredList = new ArrayList<>();
                    for (Producto row : productoList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNombre().toLowerCase().contains(charString.toLowerCase()) || String.valueOf(row.getId()).contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    productoListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productoListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productoListFiltered = (ArrayList<Producto>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ProductosAdapterListener {
        void onProductoSelected(Producto producto);
    }
}
