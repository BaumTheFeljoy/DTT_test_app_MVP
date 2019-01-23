package com.example.RSRpechhulpTest.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.RSRpechhulpTest.AboutPage.AboutPageActivity;
import com.example.RSRpechhulpTest.R;
import com.example.RSRpechhulpTest.utility.ActivityUtils;

public class MainActivity extends AppCompatActivity {
    float dpWidth = 0;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Replace ActionBar with Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //Add Fragment to Activity
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(mainFragment == null){
            mainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mainFragment,R.id.contentFrame);
        }

        mainPresenter = new MainPresenter(mainFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //The toolbar is not part of the MainFragment so it can't be accessed through that
        if(mainPresenter.isTabletScreen(getWindowManager().getDefaultDisplay(),getResources().getDisplayMetrics().density)) return false;// don't enable the i-button on the toolbar on devices with dpWidth>=600
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    /**
     * Toolbar options selected method with easy expandability
     * @param item The selected item
     * @return boolean depending on success of call
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.info_btn:
                Intent intent = new Intent(this, AboutPageActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
