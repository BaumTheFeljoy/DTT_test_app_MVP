package com.example.RSRpechhulpTest.Map;

import android.content.Context;

public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View mapView;
    private final Context mapContext;

    public MapPresenter(MapContract.View mapView, Context context){
        this.mapView = mapView;
        mapContext = context;

        this.mapView.setPresenter(this);
    }

    @Override
    public void requestLocation() {


    }

    @Override
    public void startLocationUpdates() {

    }

    @Override
    public void start() {

    }

    public void changePopupWrapper(){

    }
}
