package com.example.RSRpechhulpTest.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.example.RSRpechhulpTest.utility.CustomInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements MapContract.View {
    //Finals
    private final static int REQUEST_ENABLE_GPS = 1;
    private final static int REQUEST_ENABLE_WIFI = 2;
    private final static int REQUEST_ENABLE_DATA = 3;
    private final static float ZOOM_LEVEL = 16.0f;  //Default zoom level on launch

    private MapContract.Presenter presenter;
    private Context mapContext;

    //Map related
    private GoogleMap map;
    private Marker marker;
    private String address = "default";
    private Boolean initialPositionSet = false;

    public MapFragment(){}

    public static MapFragment newInstance(){
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapContext = getContext();
        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        Button call_btn = root.findViewById(R.id.call_btn);
        Button dial_btn = root.findViewById(R.id.dial_btn);
        Button popupClose = root.findViewById(R.id.popup_close);

        //make sure that they exist because on tablet these buttons are not there
        if (dial_btn != null) {
            dial_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    presenter.requestDial();
                }
            });

            popupClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    presenter.changePopupWrapper();
                }
            });

            call_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    presenter.changePopupWrapper();
                }
            });
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        if (!isLocationEnabled())return; //Only open once alert at once
        else if(!isNetworkAvailable())return;
    }

    @Override
    public void initMarker() {
        marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(52.4, 4.8))
                .title("Uw Locatie:")
                .snippet(address + "\n" + mapContext.getString(R.string.marker_text))
                .visible(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
    }

    @Override
    public void updateMarker(LatLng latLng, String address) {
        marker.setVisible(true);
        marker.setSnippet(address + "\n" + mapContext.getString(R.string.marker_text));
        marker.showInfoWindow();
        marker.setPosition(latLng);

        if(!initialPositionSet) {//After the camera has been centered on the marker, future updates shouldn't move it again.
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            initialPositionSet = true;
        }
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL));
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(mapContext));

        initMarker();

        presenter.requestLocation();
        presenter.startLocationUpdates();
    }

    /**
     * Displays alert message in case the GPS is turned off.
     * Has options to go back or change setting
     */
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mapContext);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(mapContext);
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

    /**
     * Handles the activity results after opening menus for GPS or Internet settings
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println("onActivityResult Fragment called");
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case REQUEST_ENABLE_GPS:{
                    presenter.requestLocation();
                    presenter.startLocationUpdates();
                break;
            }case REQUEST_ENABLE_WIFI:{
                //isNetworkAvailable(); redundant because of onResume()
                break;
            }case REQUEST_ENABLE_DATA:{
                //isNetworkAvailable();
                break;
            }
        }
    }

    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo == null || !activeNetworkInfo.isConnected()){
            buildAlertMessageNoNetwork();
            return false;
        }
        return true;
    }

    @Override
    public void dialNumber(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(getString(R.string.rsr_number)));
        startActivity(intent);
    }
}
