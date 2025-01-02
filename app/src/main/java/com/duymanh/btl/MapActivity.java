package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle("Bản đồ công ty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Trở về activity trước đó
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //21.0227346,105.7957637,13
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng sydney = new LatLng(21.0227346,105.7957637);
        myMap.addMarker(new MarkerOptions().position(sydney).title("Hà Đông - Hà Nội"));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}