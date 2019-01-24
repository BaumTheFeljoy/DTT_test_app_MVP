package com.example.RSRpechhulpTest.Map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.RSRpechhulpTest.utility.PermissionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MapModel {
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 sec */

    private Activity mapActivity;
    public PermissionManager permissionManager;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Boolean LocationUpdatesRunning = false;

    public MapModel(Activity activity) {
        permissionManager = new PermissionManager(activity);

        mapActivity = activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mapActivity);
    }

    /**
     * Gets the most recent available location, onCompleteListener has to handle the result
     */
    public void getDeviceLocation(OnCompleteListener onCompleteListener) {
        try {
            Task locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(mapActivity, onCompleteListener);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Trigger new location updates at interval
     * source: https://github.com/codepath/android_guides/wiki/Retrieving-Location-with-LocationServices-API
     */
    //TODO: add stopLocationUpdates method and call at onPause()
    public void startLocationUpdates(LocationCallback locationCallback) {

        if (LocationUpdatesRunning) {//Making sure that there is only on LocationUpdate running at once
            return;
        } else {
            // Create the location request to start receiving updates
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

            // Create LocationSettingsRequest object using location request
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();

            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(mapActivity);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            if (ActivityCompat.checkSelfPermission(mapActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
            LocationUpdatesRunning = true; //set to true so that this doesn't get started more than once
        }
    }

    /**
     * Calculates the address from LatLng coordinates using a Geocoder
     * @param location
     * @return address of given location as string
     */
    public String getAddress(Location location){
        Geocoder geocoder;
        String address = "default";
        List<Address> addresses;
        geocoder = new Geocoder(mapActivity.getBaseContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
