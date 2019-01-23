package com.example.RSRpechhulpTest.AboutPage;

public class AboutPagePresenter implements AboutPageContract.Presenter {
    //AboutPage currently doesn't need a Model

    public AboutPagePresenter(AboutPageContract.View aboutPageView) {
        aboutPageView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
