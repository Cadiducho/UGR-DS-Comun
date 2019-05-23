package com.ugr.farmaciads.ui.carrito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ugr.farmaciads.R;

public class CarritoFragment extends Fragment {

    private CarritoViewModel carritoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        carritoViewModel =
                ViewModelProviders.of(this).get(CarritoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_carrito, container, false);
        final TextView textView = root.findViewById(R.id.text_carrito);
        carritoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}