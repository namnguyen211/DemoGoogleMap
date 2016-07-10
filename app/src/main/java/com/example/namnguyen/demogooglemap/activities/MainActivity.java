package com.example.namnguyen.demogooglemap.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.models.Location;
import com.example.namnguyen.demogooglemap.models.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private GoogleMap map;
    private List<Venue> list = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private android.location.Location mLastLocation;
    Double a,b;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = getIntent().getParcelableArrayListExtra("list");
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){

                    } else{
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatitude = mLastLocation.getLatitude();
                    mLongitude = mLastLocation.getLongitude();
                    Log.d(TAG, String.valueOf(mLatitude));
                    Log.d(TAG, String.valueOf(mLongitude));
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d(TAG, "Connection Suspended");
            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "Connection Failed");
                    }
                }).addApi(LocationServices.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

//        a = Double.valueOf(getIntent().getStringExtra("LatDetail"));
//        b = Double.valueOf(getIntent().getStringExtra("LngDetail"));
        LatLng currentLocation = new LatLng(10.7960682, 106.6760491);
//        MarkerOptions vt = new MarkerOptions();
//        vt.draggable(true);
//        vt.position(currentLocation);
//        vt.title(getIntent().getStringExtra("TitleDetail"));

//        Marker  marker = map.addMarker(vt);
//        marker.showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,16));
        for (Venue v : list) {
            Location l = v.getLocation();
            LatLng latLng = new LatLng(l.getLat(), l.getLng());
            MarkerOptions vt = new MarkerOptions();
            vt.position(latLng);
            vt.title(v.getName());
            Marker  marker = map.addMarker(vt);
//            marker.showInfoWindow();
//            map.addMarker(new MarkerOptions().position(latLng).title(v.getName()));
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}
