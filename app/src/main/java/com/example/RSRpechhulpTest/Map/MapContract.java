package com.example.RSRpechhulpTest.Map;

import com.example.RSRpechhulpTest.BasePresenter;
import com.example.RSRpechhulpTest.BaseView;
import com.google.android.gms.maps.OnMapReadyCallback;

public interface MapContract {

    interface View extends BaseView<Presenter>, OnMapReadyCallback {

        void initMarker();

        void updateMarker();

        //alertMessages

        //change popupWrapper
    }

    interface Presenter extends BasePresenter {

        void requestLocation();

        void startLocationUpdates();

        //permissions
    }
}
