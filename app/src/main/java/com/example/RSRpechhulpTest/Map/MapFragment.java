package com.example.RSRpechhulpTest.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.RSRpechhulpTest.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements MapContract.View {
    private final static int REQUEST_ENABLE_GPS = 1;
    private final static int REQUEST_ENABLE_WIFI = 2;
    private final static int REQUEST_ENABLE_DATA = 3;

    private MapContract.Presenter presenter;
    private GoogleMap map;

    public MapFragment(){}

    public static MapFragment newInstance(){
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        Button call_btn = getActivity().findViewById(R.id.call_btn);
        Button dial_btn = root.findViewById(R.id.dial_btn);
        Button popupClose = root.findViewById(R.id.popup_close);

        //make sure that it exists because on phone screen the button is in the toolbar, not in the fragment
        if (dial_btn != null) {
            dial_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    //presenter.aboutButtonPressed();
                }
            });
        }
        if (popupClose != null) {
            popupClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    //presenter.aboutButtonPressed();
                }
            });
        }
        if (call_btn != null){
            call_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    //presenter.changePopupWrapper();
                }
            });
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void initMarker() {

    }

    @Override
    public void updateMarker() {

    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


    }

    /**
     * Displays alert message in case the GPS is turned off.
     * Has options to go back or change setting
     */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.gps_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.activate), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, REQUEST_ENABLE_GPS);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        getActivity().finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Displays alert message in case the Internet is turned off.
     * Has options to go back, to WIFI or to DATA settings.
     */
    private void buildAlertMessageNoNetwork() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.no_network_message))
                .setTitle(getString(R.string.network_off))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.open_wifi_settings), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivityForResult(enableGpsIntent, REQUEST_ENABLE_WIFI);
                    }
                })
                .setNegativeButton(getString(R.string.open_data_settigns),new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                        startActivityForResult(enableGpsIntent, REQUEST_ENABLE_DATA);
                    }
                })
                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        getActivity().finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
            buildAlertMessageNoNetwork();
            return false;
        }
        return true;
    }
}
