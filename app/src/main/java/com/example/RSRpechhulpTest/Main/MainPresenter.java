package com.example.RSRpechhulpTest.Main;

import android.util.DisplayMetrics;
import android.view.Display;

public class MainPresenter implements MainContract.Presenter {
    //Main currently doesn't need a Model

    private final MainContract.View mainView;

    public MainPresenter(MainContract.View mainView){
        this.mainView = mainView;
        this.mainView.setPresenter(this);
    }

    @Override
    public void aboutButtonPressed() {
        mainView.openAboutPageActivity();
    }

    @Override
    public void mapsButtonPressed() {
        mainView.openMapsActivity();
    }

    @Override
    public void start() {

    }

    /**
     * Returns true if the screen is wider than 600dp (breakpoint for tablets usually)
     * @param display the device display
     * @param density the display density
     * @return
     */
    @Override
    public boolean isTabletScreen(Display display, float density){
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float dpWidth = outMetrics.widthPixels / density;

        if(dpWidth>=600){
            return true;
        }
        return false;
    }

}
