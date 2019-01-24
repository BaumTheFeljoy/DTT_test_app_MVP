package com.example.RSRpechhulpTest.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Handles checking and acquiring of permissions for the MapActivity
 */
public class PermissionManager {
    //Finals
    private final static String[] PERMISSIONS_LOC = {Manifest.permission.ACCESS_FINE_LOCATION};
    private final static String[] PERMISSIONS_CALL = {Manifest.permission.CALL_PHONE};
    private final static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final static int PERMISSIONS_REQUEST_CALL = 2;

    //Permission status
    public Boolean callPermissionGranted = false;
    public Boolean locationPermissionGranted = false;

    Activity activity;

    /**
     * Handles Location and Call permissions
     * @param activity that needs these permissions to function properly
     */
    public PermissionManager(Activity activity) {
        this.activity = activity;
        initPermissions();
    }

    /**
     * Set permissions according to previous given permissions
     */
    public void initPermissions(){
        if(ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED)
            callPermissionGranted = true;
        if(ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)locationPermissionGranted = true;
    }

    public void getCallPermission(){
        if(ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED){
            callPermissionGranted = true;
        }else{//not granted, open permission dialog
            ActivityCompat.requestPermissions(activity,PERMISSIONS_CALL, PERMISSIONS_REQUEST_CALL);
        }
    }

    public void getLocationPermission(){
        if(ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true;
        }else {//not granted, open permission dialog
            ActivityCompat.requestPermissions(activity,PERMISSIONS_LOC,PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void setCallPermission(Boolean bool){
        callPermissionGranted = bool;
    }

    public void setLocationPermission(Boolean bool){
        locationPermissionGranted = bool;
    }
}
