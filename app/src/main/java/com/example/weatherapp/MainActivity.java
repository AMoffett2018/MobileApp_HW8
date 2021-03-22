package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

//private FusedLocationProviderClient fusedLocationClient;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.weatherapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickSearch(View view) {
        EditText editText = (EditText) findViewById(R.id.searchField);
        String message = editText.getText().toString();
        sendSearch(view,message);
    }

    /*public void locSearch(View view) {
        //Ask for location permission
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getLocation();
            System.out.println("PERMISSION ALREADY GRANTED FOR LOCATION, DOING ACTION");
        }else {
            //directly ask for the permission.
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },LOCATION_REQUEST_CODE);
        }
    }*/

    public void sendSearch(View view, String message) {
        Intent intent = new Intent(this, WeatherPage.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}