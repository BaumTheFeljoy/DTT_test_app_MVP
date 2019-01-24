package com.example.RSRpechhulpTest.utility;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.RSRpechhulpTest.R;

/**
 * Manages the layout changes that can occur in the MapsActivity
 */
public class MapLayoutManager {
    //Layout parts
    private ConstraintLayout popupWrapper;
    private Button callButton;
    private TextView darkenMap;

    private Activity mActivity;

    public MapLayoutManager(Activity activity) {
        mActivity = activity;

        popupWrapper = mActivity.findViewById(R.id.popup_wrapper);
        callButton = mActivity.findViewById(R.id.call_btn);
        darkenMap = mActivity.findViewById(R.id.darken_map);
    }

    //open close popupWrapper depending on current visibility
    public void changePopupWrapper(){
        if (mActivity.findViewById(R.id.popup_wrapper).getVisibility()==View.GONE){
            popupWrapper.setVisibility(View.VISIBLE);
            callButton.setVisibility(View.GONE);
            darkenMap.setVisibility(View.VISIBLE);
        }else{
            popupWrapper.setVisibility(View.GONE);
            callButton.setVisibility(View.VISIBLE);
            darkenMap.setVisibility(View.GONE);
        }
    }

}
