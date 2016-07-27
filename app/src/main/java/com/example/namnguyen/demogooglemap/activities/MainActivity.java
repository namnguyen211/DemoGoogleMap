package com.example.namnguyen.demogooglemap.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.namnguyen.demogooglemap.DirectionFinder;
import com.example.namnguyen.demogooglemap.DirectionFinderListener;
import com.example.namnguyen.demogooglemap.R;
import com.example.namnguyen.demogooglemap.models.foursquare.Location;
import com.example.namnguyen.demogooglemap.models.foursquare.Route;
import com.example.namnguyen.demogooglemap.models.foursquare.Venue;
import com.example.namnguyen.demogooglemap.models.google.Geometry;
import com.example.namnguyen.demogooglemap.models.google.Result;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,DirectionFinderListener {

    private static final String TAG = "MapsActivity";
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private GoogleMap map;
    private List<Venue> listFourSquare = new ArrayList<>();
    private List<Result> listGoogle = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private android.location.Location mLastLocation;
    Double a,b;
    private double mLatitude;
    private double mLongitude;
    private Marker  marker;
    private FloatingActionButton fab;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private List<Marker> markerList = new ArrayList<>();
    private LatLng detailLocation;
    private LatLngBounds.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFourSquare = getIntent().getParcelableArrayListExtra("listFourSquare");
        listGoogle = getIntent().getParcelableArrayListExtra("listGoogle");
        setContentView(R.layout.activity_main);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        builder = new LatLngBounds.Builder();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
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

        LatLng currentLocation = new LatLng(10.7960682, 106.6760491);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,16));

        Intent intent = this.getIntent();
        if(intent != null){
//            String strdata = intent.getExtras().getString("MainActivity");
            String strdata = intent.getStringExtra("MainActivity");

            if(strdata.equals("DetailActivity")){
                a = Double.valueOf(getIntent().getStringExtra("LatDetail"));
                b = Double.valueOf(getIntent().getStringExtra("LngDetail"));
                detailLocation = new LatLng(a,b);
                map.addMarker( new MarkerOptions().position(detailLocation).title(getIntent().getStringExtra("TitleDetail")));
                CameraUpdate movezoomCamera = CameraUpdateFactory.newLatLngZoom(detailLocation,18);
                map.animateCamera(movezoomCamera,3000,null);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendRequest();
                        map.clear();
                    }
                });

            }if(strdata.equals("FourSquareSearchActivity")){
                fab.setVisibility(View.GONE);
                for (Venue v : listFourSquare) {
                    Location l = v.getLocation();
                    LatLng latLng = new LatLng(l.getLat(), l.getLng());
//                    v.getId();
//                    MarkerOptions vt = new MarkerOptions();
//                    vt.position(latLng);
//                    vt.title(v.getName());
//                    vt.snippet(v.getId());
//                    marker  = map.addMarker(vt);

                    marker =  map.addMarker(new MarkerOptions().position(latLng).title(v.getName()).snippet(v.getId()));
                    markerList.add(marker);
                    for(Marker m : markerList){
                        builder.include(m.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,50);
                    map.animateCamera(cu);
                }

            }if(strdata.equals("GoogleSearchActivity")){
                fab.setVisibility(View.GONE);
                for (Result r : listGoogle) {
                    Geometry g = r.getGeometry();
                    com.example.namnguyen.demogooglemap.models.google.Location l = g.getLocation();
                    LatLng latLng = new LatLng(l.getLat(), l.getLng());
                    marker =  map.addMarker(new MarkerOptions().position(latLng).title(r.getName()).snippet(r.getId()));
                    markerList.add(marker);
                    for(Marker m : markerList){
                        builder.include(m.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,50);
                    map.animateCamera(cu);
                }
            }
            googleMap.setOnMarkerClickListener(this);
        }
    }

    private void sendRequest() {
        Double c = 10.7960682;
        Double d = 106.6760491;
        try {
            new DirectionFinder(this,c ,d ,a,b).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra("DetailActivity","MainActivity");
        intent.putExtra("TitleMain",marker.getTitle());
        intent.putExtra("IdMain",marker.getSnippet());
//        intent.putExtra("PhotoMain",marker.getSnippet());
        intent.putExtra("LatMain",String.valueOf(marker.getPosition().latitude));
        intent.putExtra("LngMain",String.valueOf(marker.getPosition().longitude));
        startActivity(intent);
        return true;
    }

    @Override
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {

            LatLng origin = new LatLng(route.startLocation.latitude, route.startLocation.longitude);
            LatLng target = new LatLng(route.endLocation.latitude, route.endLocation.longitude);
            LatLngBounds.Builder b = new LatLngBounds.Builder();
            b.include(origin);
            b.include(target);
            LatLngBounds bounds = b.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200, 100, 5);

            map.animateCamera(cu);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(map.addPolyline(polylineOptions));
        }
    }


}
