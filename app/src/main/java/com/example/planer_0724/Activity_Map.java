package com.example.planer_0724;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class Activity_Map extends FragmentActivity implements OnMapReadyCallback {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);


            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
            }
            mapFragment.getMapAsync(this);
        }
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // ...
        Marker marker = new Marker();
        marker.setPosition(new LatLng(36.763695, 127.281796));
        marker.setMap(naverMap);
    }
}