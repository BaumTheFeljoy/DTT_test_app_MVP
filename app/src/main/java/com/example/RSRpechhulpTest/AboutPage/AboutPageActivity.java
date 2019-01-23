package com.example.RSRpechhulpTest.AboutPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.RSRpechhulpTest.R;
import com.example.RSRpechhulpTest.utility.ActivityUtils;

public class AboutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        //Replace ActionBar with Toolbar with back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Add Fragment to Activity
        AboutPageFragment aboutPageFragment = (AboutPageFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(aboutPageFragment == null){
            aboutPageFragment = AboutPageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),aboutPageFragment,R.id.contentFrame);
        }
        new AboutPagePresenter(aboutPageFragment);
    }
}
