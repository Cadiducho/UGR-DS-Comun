package com.ugr.farmaciads.ui.mapa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ugr.farmaciads.R;
import com.ugr.farmaciads.data.FarmaciasService;
import com.ugr.farmaciads.ui.farmacias.FarmaciasFragment;

import java.util.ArrayList;
import java.util.List;

import p3.farmacia.modelo.Farmacia;

public class MapaFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter, LocationListener, GoogleMap.OnInfoWindowClickListener {

    private View fragmentRoot;
    private View infoView;

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Location currentLocation;
    private List<Farmacia> farmaciaList = new ArrayList<>();

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        fragmentRoot = inflater.inflate(R.layout.fragment_mapa, container, false);
        infoView = inflater.inflate(R.layout.info_view_map, container, false);

        // Pedir permisos de mapa
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try {
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            else {
                Log.e("Test", "network provider unavailable");
                if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
                else
                    Log.e("Test", "passive provider unavailable");
            }

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            currentLocation = lastKnownLocation;

            if(lastKnownLocation != null) {
                Log.e("Test", lastKnownLocation.getLatitude() + ", " + lastKnownLocation.getLongitude());
                locationManager.removeUpdates(this);
            }
        } catch(SecurityException se) {
            Log.e("SECURITY EXCEPTION", "FAILING ON CREATE - LOCATION MANAGER");
            se.printStackTrace();
        }

        if(mapFragment == null)
            mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        mapFragment.getMapAsync(this);

        return fragmentRoot;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        googleMap.setInfoWindowAdapter(this);
        googleMap.setOnInfoWindowClickListener(this);

        try {
            googleMap.setMyLocationEnabled(true);
        }catch (SecurityException se) {
            Log.e("SECURITY EXCEPTION", "SETTING MY LOCATION ENABLED");
            se.printStackTrace();
        }

        setupLocation();
        setUpPharmacies();
    }

    public void setupLocation() {
        if (currentLocation != null) {
            // Crear marca en el mapa
            MarkerOptions marker = new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("You are here");

            // Cambiar estilo del icono
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            // Añadir la marca al mapa
            googleMap.addMarker(marker);

            // Mover y centrar la cámara en la marca
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(14).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    public void setUpPharmacies() {
        new FarmaciasService().getFarmacias(getContext(), (farmacias) -> {
            farmaciaList.addAll(farmacias);

            MapaFragment.this.getActivity().runOnUiThread(() -> {
                Log.i("MAP", "Agregando lista de farmacias " + farmaciaList.toString());
                for (Farmacia farmacia : farmaciaList) {
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(farmacia.getLatitud(), farmacia.getLongitud())).title(farmacia.getNombre());

                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    googleMap.addMarker(marker);
                    Log.w("MAP", "Añadiendo " + marker.getTitle());
                }
            });
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.w("CLICK", "Click on " + marker.getTitle());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        Log.e("INFO CONTENTS MARKER", "ENTER");

        TextView pharmacyName = (TextView) infoView.findViewById(R.id.farmacia_info_name);

        boolean found = false;

        for (Farmacia farmacia : farmaciaList) {
            if (farmacia.getNombre().equals(marker.getTitle())) {

                Log.e("Entra IF", farmacia.getNombre() + " - " + marker.getTitle());

                pharmacyName.setText(farmacia.getNombre());
                found = true;
            }
        }

        if(!found) {
            pharmacyName.setText("Estás aquí");
        }

        return infoView;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation = location;
            setupLocation();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}