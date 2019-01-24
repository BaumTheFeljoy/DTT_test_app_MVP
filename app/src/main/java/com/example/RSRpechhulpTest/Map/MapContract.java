package com.example.RSRpechhulpTest.Map;

import com.example.RSRpechhulpTest.BasePresenter;
import com.example.RSRpechhulpTest.BaseView;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;

public interface MapContract {

    interface View extends BaseView<Presenter>, OnMapReadyCallback {

        void initMarker();

        void updateMarker(LatLng latLng, String address);

        boolean isLocationEnabled();

        boolean isNetworkAvailable();

        void dialNumber();

        //alertMessages

        //change popupWrapper
    }

    interface Presenter extends BasePresenter {

        OnCompleteListener createOnCompleteListener();

        LocationCallback createLocationCallback();

        void requestDial();

        void updateCallPermission();

        void updateLocationPermission();

        void requestLocation();

        void startLocationUpdates();

        void changePopupWrapper();

        //permissions
    }
}
