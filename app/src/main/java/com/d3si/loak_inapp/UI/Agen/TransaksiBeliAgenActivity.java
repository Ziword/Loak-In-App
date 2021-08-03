package com.d3si.loak_inapp.UI.Agen;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d3si.loak_inapp.Adapter.Agen.AdapterSelectorLoak;
import com.d3si.loak_inapp.Adapter.Member.AdapterProsesTransaksiMember;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Constructor.ConstAgen;
import com.d3si.loak_inapp.Constructor.ConstTransaksi;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.NotificationCaller;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransaksiBeliAgenActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double longitude = 0.0, latitude = 0.0;
    MyDB db;
    DatabaseReference databaseReference;
    BottomSheetDialog bottomSheetDialog;

    TextView backMenu;

    private RecyclerView rv;
    private AdapterSelectorLoak adapterSelectorLoak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_beli_agen);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTransaksiBeliAgen);

        DBBuilder();

        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi");

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv = findViewById(R.id.rvSelectorLoak);
        RecyclerView.LayoutManager layRv = new LinearLayoutManager(TransaksiBeliAgenActivity.this, RecyclerView.HORIZONTAL, false);
        rv.setLayoutManager(layRv);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapterSelectorLoak = new AdapterSelectorLoak();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_item);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bMapScaled);

        getLocation();

        Call<ArrayList<ConstTransaksi>> call = db.getTransaksiJual();
        call.enqueue(new Callback<ArrayList<ConstTransaksi>>() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public void onResponse(Call<ArrayList<ConstTransaksi>> call, Response<ArrayList<ConstTransaksi>> response) {
                if(response.body().size()>0)
                {
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        Double lat = Double.parseDouble(response.body().get(i).getLAT_MEMBER());
                        Double lng = Double.parseDouble(response.body().get(i).getLONG_MEMBER());
                        String namaBarang = response.body().get(i).getNAMA_BARANG_LOAK();
                        MarkerOptions barangLoc = new MarkerOptions().position(new LatLng(lat,lng))
                                .title(namaBarang)
                                .snippet(response.body().get(i).getID_TRANSAKSI())
                                .icon(icon);
                        mMap.addMarker(barangLoc);

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                bottomSheetDialog = new BottomSheetDialog(TransaksiBeliAgenActivity.this, R.style.BottomSheetDialogTheme);
                                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bs_detail_barang_agen, findViewById(R.id.bs_container_detailBarang));

                                Button btnBeli = bottomSheetView.findViewById(R.id.btn_ambil_loak);
                                btnBeli.setVisibility(View.VISIBLE);

                                TextView idBarang = bottomSheetView.findViewById(R.id.row_detail_id_barang);
                                TextView beratBarang = bottomSheetView.findViewById(R.id.row_detail_berat_barang);
                                TextView namaPenjual = bottomSheetView.findViewById(R.id.row_detail_nama_penjual);
                                TextView namaBarang = bottomSheetView.findViewById(R.id.row_detail_nama_barang);
                                TextView jenisLoak = bottomSheetView.findViewById(R.id.row_detail_jenis_loak);
                                TextView catatanBarang = bottomSheetView.findViewById(R.id.row_detail_catatan_barang);
                                TextView alamatBarang = bottomSheetView.findViewById(R.id.row_detail_alamat_barang);
                                LinearLayout btnGmap = bottomSheetView.findViewById(R.id.btn_gmap);
                                LinearLayout btnWa = bottomSheetView.findViewById(R.id.btn_wa);

                                Call<ConstTransaksi> call = db.getDetailTransaksi(marker.getSnippet());
                                call.enqueue(new Callback<ConstTransaksi>() {
                                    @Override
                                    public void onResponse(Call<ConstTransaksi> call, Response<ConstTransaksi> response) {
                                        idBarang.setText(response.body().getID_TRANSAKSI());
                                        namaBarang.setText("Nama Barang : "+response.body().getNAMA_BARANG_LOAK());
                                        beratBarang.setText(response.body().getBERAT_BARANG());
                                        namaPenjual.setText("Nama Penjual : " + response.body().getNAMA_LENGKAP_MEMBER());
                                        jenisLoak.setText("Jenis Loak : "+response.body().getJENIS_BARANG());
                                        catatanBarang.setText(response.body().getKETERANGAN_TRANSAKSI());
                                        alamatBarang.setText(response.body().getALAMAT_TRANSAKSI());

                                        btnGmap.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Double latMarker = marker.getPosition().latitude;
                                                Double longMarker = marker.getPosition().longitude;
                                                openGmap(latMarker, longMarker);
                                            }
                                        });

                                        btnWa.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                openWa(response.body().getNO_TELP_MEMBER());
                                            }
                                        });

                                        btnBeli.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(TransaksiBeliAgenActivity.this);
                                                dialog.setMessage("Konfirmasi Pengambilan Order").setCancelable(true).setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ambilOrder(response.body().getID_TRANSAKSI());
                                                        dialog.dismiss();
                                                    }
                                                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<ConstTransaksi> call, Throwable t) {

                                    }
                                });

                                bottomSheetDialog.setContentView(bottomSheetView);
                                bottomSheetDialog.show();
                                return false;
                            }
                        });
                    }

                    adapterSelectorLoak.setContext(TransaksiBeliAgenActivity.this);
                    adapterSelectorLoak.setItems(response.body());
                    adapterSelectorLoak.setmMap(mMap);
                    if(latitude!= 0.0 || longitude!= 0.0)
                    {
                        adapterSelectorLoak.setPosLatLng(new LatLng(latitude, longitude));
                    }

                    rv.setAdapter(adapterSelectorLoak);
                    adapterSelectorLoak.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConstTransaksi>> call, Throwable t) {

            }
        });
    }

    private void ambilOrder(String id_transaksi)
    {
        //  Ubah data pada firebase
        String uidAgen = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("User").child(uidAgen).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String id_agen = dataSnapshot.child("ID_AGEN").getValue().toString();
                databaseReference.child(id_transaksi).child("ID_AGEN").setValue(id_agen);
                databaseReference.child(id_transaksi).child("ID_STATUS_TRANSAKSI").setValue("ST2");
                //  Ubah data db

                DBBuilder();
                Call<ResponseBody> call = db.updateTransaksi(id_transaksi, id_agen, "ST2");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i = new Intent(TransaksiBeliAgenActivity.this, HomeActivityAgen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        bottomSheetDialog.dismiss();
                        NotificationCaller notificationCaller = new NotificationCaller(TransaksiBeliAgenActivity.this);
                        notificationCaller.notifAmbilOrderan();
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void openWa(String no_telp_member)
    {
        Uri uri = Uri.parse("smsto:" + no_telp_member);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, "Aplikasi LoakIn"));
    }

    private void openGmap(Double latMarker, Double longMarker)
    {
        Uri navigationIntentUri = Uri.parse("google.navigation:q=" + latMarker + "," + longMarker);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }

    private void getLocation() {
        Location location = null;
        LocationManager lm = (LocationManager) TransaksiBeliAgenActivity.this.getSystemService(TransaksiBeliAgenActivity.this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(TransaksiBeliAgenActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(TransaksiBeliAgenActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(TransaksiBeliAgenActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            Location l = null;
            LocationManager mLocationManager = (LocationManager)TransaksiBeliAgenActivity.this.getSystemService(Context.LOCATION_SERVICE);
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