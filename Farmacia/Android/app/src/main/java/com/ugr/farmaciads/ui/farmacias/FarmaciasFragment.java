package com.ugr.farmaciads.ui.farmacias;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.moshi.Types;
import com.ugr.farmaciads.R;
import com.ugr.farmaciads.data.FarmaciasService;
import com.ugr.farmaciads.data.RestConnector;
import com.ugr.farmaciads.ui.MyDividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import p3.farmacia.modelo.ApiResponse;
import p3.farmacia.modelo.Farmacia;

public class FarmaciasFragment extends Fragment implements FarmaciasAdapter.FarmaciasAdapterListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Farmacia> farmaciaList;
    private FarmaciasAdapter mAdapter;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_farmacias, container, false);

        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout_farmacias);
        recyclerView = root.findViewById(R.id.recycler_view_farmacias);
        farmaciaList = new ArrayList<>();
        mAdapter = new FarmaciasAdapter(getContext(), farmaciaList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        fetchFarmacias();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            fetchFarmacias();
        });

        return root;
    }

    private void fetchFarmacias() {
        new FarmaciasService().getFarmacias(getContext(), (farmacias -> {
            FarmaciasFragment.this.getActivity().runOnUiThread(() -> {
                farmaciaList.clear();
                farmaciaList.addAll(farmacias);

                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            });
        }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);

        menu.clear();

        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        searchView = (SearchView) item.getActionView();
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.w("SEARCH", "Buscando " + query);
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                Log.w("SEARCH", "Buscando " + query);
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public void onFarmaciaSelected(Farmacia farmacia) {
        Toast.makeText(getContext(), "Selected: " + farmacia.getNombre() + ", " + farmacia.getId(), Toast.LENGTH_LONG).show();
    }
}