package com.adrian_971029.infobook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.adrian_971029.infobook.io.ApiAdapter;
import com.adrian_971029.infobook.io.ApiService;
import com.adrian_971029.infobook.model_map.MyPlaces;
import com.adrian_971029.infobook.model_map.Result;
import com.adrian_971029.infobook.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.bottom_navegation)
    BottomNavigationView bottomNavigationView;

    private static final int MY_PERMISSION_CODE = 1000 ;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private double latitude, longitude;
    private Location mLastLocation;
    private Marker mMarker;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_library:
                        nearByPlace(getString(R.string.lbl_book_store));
                        break;
                    case R.id.action_biblioteca:
                        nearByPlace(getString(R.string.lbl_library));
                        break;
                    case R.id.action_back:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    private void nearByPlace(String placeType) {
        mMap.clear();
        String url = getUrl(latitude,longitude,placeType);

        ApiService service = ApiAdapter.getApiService(Constants.GOOGLE_API_URL);
        Call<MyPlaces> myPlacesCall = service.getNearByPlaces(url);

        myPlacesCall.enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                if (response.isSuccessful()){
                    for (int i = 0 ;i<response.body().getResults().size();i++) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        Result googlePlace = response.body().getResults().get(i);
                        double lat = googlePlace.getGeometry().getLocation().getLat();
                        double lng = googlePlace.getGeometry().getLocation().getLng();
                        String placeName = googlePlace.getName();
                        String vicinity = googlePlace.getVicinity();
                        LatLng latLng = new LatLng(lat,lng);
                        markerOptions.position(latLng);
                        markerOptions.title(placeName);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name));

                        mMap.addMarker(markerOptions);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                    }
                }
            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {

            }
        });

    }

    private String getUrl(double latitude, double longitude, String placeType) {
        StringBuilder googlePlaceUrl = new StringBuilder(getString(R.string.url_nearplaces));
        googlePlaceUrl.append(getString(R.string.location_url)+latitude+","+longitude);
        googlePlaceUrl.append(getString(R.string.radius_url)+10000);
        googlePlaceUrl.append(getString(R.string.type_url)+placeType);
        googlePlaceUrl.append(getString(R.string.sensor_url));
        googlePlaceUrl.append(getString(R.string.key_url)+getResources().getString(R.string.google_maps_key));
        Log.d(getString(R.string.lbl_getUlr),googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }

    private boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PERMISSION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buidGoogleApiClient();
                            mMap.setMyLocationEnabled(true);
                        }
                    }
                } else {
                    Toast.makeText(this, R.string.lbl_permissao_denegada,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                buidGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buidGoogleApiClient();
            mMap.setMyLocationEnabled(false);
        }

    }

    private synchronized void buidGoogleApiClient() {
        mGoogleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mMarker != null) {
            mMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.lbl_minha_posicao))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
