package com.example.RSRpechhulpTest.Main;

import android.view.Display;

import com.example.RSRpechhulpTest.BasePresenter;
import com.example.RSRpechhulpTest.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter>{

        void openAboutPageActivity();

        void openMapsActivity();
    }

    interface Presenter extends BasePresenter {
        boolean isTabletScreen(Display display, float density);

        void aboutButtonPressed();

        void mapsButtonPressed();
    }

}
