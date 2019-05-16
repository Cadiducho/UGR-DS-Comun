package com.ugr.farmaciads.ui;

import android.os.Bundle;

import com.ugr.farmaciads.R;
import com.ugr.farmaciads.adapter.FarmaciasAdapter;
import com.ugr.farmaciads.data.FarmaciasService;
import com.ugr.farmaciads.data.RetrofitInstance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import p3.farmacia.modelo.Farmacia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FarmaciasAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FarmaciasService service = RetrofitInstance.getRetrofitInstance().create(FarmaciasService.class);

        Call<List<Farmacia>> call = service.getFarmacias();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<List<Farmacia>>() {
            @Override
            public void onResponse(Call<List<Farmacia>> call, Response<List<Farmacia>> response) {
                generateEmployeeList(response.body());
            }

            @Override
            public void onFailure(Call<List<Farmacia>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateEmployeeList(List<Farmacia> farmDataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_farmacias_list);

        adapter = new FarmaciasAdapter(farmDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }
}
