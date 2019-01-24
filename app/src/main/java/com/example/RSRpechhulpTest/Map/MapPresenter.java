package com.example.RSRpechhulpTest.Map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.RSRpechhulpTest.utility.MapLayoutManager;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View mapView;
    private MapModel mapModel;
    private MapLayoutManager mapLayoutManager;
    private Activity mapActivity;

    private OnCompleteListener onCompleteListener;
    private LocationCallback locationCallback;

    public MapPresenter(MapContract.View mapView, Activity activity){
        this.mapView = mapView;  //View
        this.mapView.setPresenter(this);
        mapActivity = activity;

        mapModel = new MapModel(activity);  //Model

        onCompleteListener = createOnCompleteListener();
        locationCallback = createLocationCallback();
    }

    @Override
    public void requestDial() {
        if(!mapModel.permissionManager.callPermissionGranted){
            mapModel.permissionManager.getCallPermission();
        }else{
            mapView.dialNumber();
        }
    }

    @Override
    public void updateCallPermission() {
        mapModel.permissionManager.getCallPermission();
    }

    @Override
    public void updateLocationPermission() {
        mapModel.permissionManager.getLocationPermission();
    }

    @Override
    public void requestLocation() {
        if(!mapModel.permissionManager.locationPermissionGranted){
            mapModel.permissionManager.getLocationPermission();
        }else{
            mapModel.getDeviceLocation(onCompleteListener);
        }
    }

    @Override
    public void startLocationUpdates() {
        if(!mapModel.permissionManager.locationPermissionGranted){
            mapModel.permissionManager.getLocationPermission();
        }else{
            mapModel.startLocationUpdates(locationCallback);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void changePopupWrapper(){
        mapLayoutManager = new MapLayoutManager(mapActivity);
        mapLayoutManager.changePopupWrapper();
    }

    private OnCompleteListener createOnCompleteListener(){
        OnCompleteListener onCompleteListener = new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    String address = mapModel.getAddress(location);
                    mapView.updateMarker(new LatLng(location.getLatitude(),location.getLongitude()),address);
                } else {
                    Log.d("log", "Current location is null. Using defaults.");
                    Log.e("log", "Exception: %s", task.getException());
                }
            }
        };
        return onCompleteListener;
    }

    private LocationCallback createLocationCallback(){
        final LocationCallback locationCallback =  new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult.getLastLocation() != null){
                    Location location = locationResult.getLastLocation();
                    String address = mapModel.getAddress(location);
                    mapView.updateMarker(new LatLng(location.getLatitude(),location.getLongitude()),address);
                }
            }
        };
        return locationCallback;
    }
}
