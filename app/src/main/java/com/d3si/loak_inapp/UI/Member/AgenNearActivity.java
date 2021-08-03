package com.d3si.loak_inapp.UI.Member;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.d3si.loak_inapp.Constructor.ConstAgen;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgenNearActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  Double longitude, latitude;
    MyDB db;

    TextView backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agen_near);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.agenNear);

        DBBuilder();

        mapFragment.getMapAsync(this);

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.agenmarker);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bMapScaled);

        Call<ArrayList<ConstAgen>> call = db.getListAgen();
        call.enqueue(new Callback<ArrayList<ConstAgen>>() {
            @Override
            public void onResponse(Call<ArrayList<ConstAgen>> call, Response<ArrayList<ConstAgen>> response) {
                {
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        Double lat = Double.parseDouble(response.body().get(i).getLATITUDE());
                        Double lng = Double.parseDouble(response.body().get(i).getLONGTITUDE());
                        String namaAgen = response.body().get(i).getNAMA_AGEN();
                        MarkerOptions agenLoc = new MarkerOptions().position(new LatLng(lat,lng))
                                .title(namaAgen)
                                .snippet("Agen Aplikasi Loak-In")
                                .icon(icon);
                        mMap.addMarker(agenLoc);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConstAgen>> call, Throwable t) {

            }
        });

        getLocation();
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }

    private void getLocation() {
        Location location = null;
        LocationManager lm = (LocationManager) AgenNearActivity.this.getSystemService(AgenNearActivity.this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(AgenNearActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AgenNearActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AgenNearActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            Location l = null;
            LocationManager mLocationManager = (LocationManager)AgenNearActivity.this.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = mLocationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
            location = bestLocation;
        }
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-7.246022, 112.737865)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            Log.d("ERROR", "Poor signal");
        }
    }
}