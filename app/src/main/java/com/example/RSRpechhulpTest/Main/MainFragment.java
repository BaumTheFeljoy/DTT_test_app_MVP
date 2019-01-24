package com.example.RSRpechhulpTest.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.RSRpechhulpTest.AboutPage.AboutPageActivity;
import com.example.RSRpechhulpTest.Map.MapActivity;
import com.example.RSRpechhulpTest.R;

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter presenter;

    public MainFragment(){

    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button button_about = root.findViewById(R.id.button_about);
        Button button_main = root.findViewById(R.id.button_main);

        //make sure that it exists because on phone screen the button is in the toolbar, not in the fragment
        if (button_about != null) {
                button_about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        presenter.aboutButtonPressed();
                    }
                });
        }
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                presenter.mapsButtonPressed();
            }
        });
        return root;
    }


    @Override
    public void openAboutPageActivity() {
        Intent intent = new Intent(getContext(), AboutPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void openMapsActivity() {
        Intent intent = new Intent(getContext(), MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
