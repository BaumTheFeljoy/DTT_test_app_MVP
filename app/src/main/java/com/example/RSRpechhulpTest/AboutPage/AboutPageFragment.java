package com.example.RSRpechhulpTest.AboutPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.RSRpechhulpTest.R;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class AboutPageFragment extends Fragment implements AboutPageContract.View {

    private AboutPageContract.Presenter presenter;

    public AboutPageFragment(){
    }

    public static AboutPageFragment newInstance(){
        return new AboutPageFragment();
    }

    @Override
    public void setPresenter(AboutPageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about_page, container, false);
        return root;
    }

}
