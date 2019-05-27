package com.ugr.farmaciads.ui.logout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ugr.farmaciads.LoginActivity;
import com.ugr.farmaciads.MainActivity;
import com.ugr.farmaciads.R;

import static android.content.Context.MODE_PRIVATE;

public class LogoutFragment extends Fragment {

    private Button logoutButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        logoutButton = root.findViewById(R.id.logout_button);

        logoutButton.setOnClickListener((l) -> {
            SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("ds", MODE_PRIVATE).edit();
            editor.remove("email");
            editor.remove("name");
            editor.remove("rol");
            editor.remove("id");
            editor.apply();

            this.getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Has cerrado sesión", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                this.getActivity().finish();
                startActivityForResult(intent,1);
            });
        });
        return root;
    }
}