package com.d3si.loak_inapp.UI.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.d3si.loak_inapp.Module.ListenOrderService;
import com.d3si.loak_inapp.Module.SessionManager;
import com.d3si.loak_inapp.UI.Agen.HomeActivityAgen;
import com.d3si.loak_inapp.UI.AuthActivity;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.ChartLaporanActivity;
import com.d3si.loak_inapp.UI.SplashActivity;
import com.d3si.loak_inapp.UI.locationSelector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class HomeActivityMember extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView namaUser, jenisUser, keProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startService(new Intent(this, ListenOrderService.class));

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navOpen, R.string.navClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        namaUser = headerView.findViewById(R.id.namaUser);
        jenisUser = headerView.findViewById(R.id.jenisUser);
        keProfil = headerView.findViewById(R.id.keProfil);
        keProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuProfilMember();
            }
        });

        getUserNavbar();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    private void getUserNavbar()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("User").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task)
            {
                try {
                    String JU, NU;
                    JU = task.getResult().child("ID_JENIS_USER").getValue().toString();
                    if (JU.equalsIgnoreCase("U2")) {
                        NU = task.getResult().child("NAMA_LENGKAP_MEMBER").getValue().toString();
                        namaUser.setText(NU);
                        jenisUser.setText("Member Loak-In");
                    } else if (JU.equalsIgnoreCase("U3")) {
                        NU = task.getResult().child("NAMA_AGEN").getValue().toString();
                        namaUser.setText(NU);
                        jenisUser.setText("Agen Loak-In");
                    }
                } catch (Exception e)
                {
                    Toast.makeText(HomeActivityMember.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loakInJual(View view)
    {
        menuJualBarang();
    }

    public void pesananMember(View view) {
        menuPesananMember();
    }

    public void menuAgenTerdekat(View view)
    {
        menuAgenTerdekat();
    }

    public void profilMember(View view) {
        menuProfilMember();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_loakInMenu:
                menuJualBarang();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_memberPesanan:
                menuPesananMember();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_agenNear:
                menuAgenTerdekat();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_memberLogout:
                menuLogout();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_laporan:
                menulaporan();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

    //    GO TO MENU
    public void menuJualBarang()
    {
        Dexter.withContext(HomeActivityMember.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport)
            {
                if (multiplePermissionsReport.areAllPermissionsGranted())
                {
                    Intent i = new Intent(HomeActivityMember.this, locationSelector.class);
                    startActivity(i);
                    Toast.makeText(HomeActivityMember.this, " Izin lokasi diberikan ",Toast.LENGTH_LONG).show();
                }

                // check for permanent denial of any permission
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied())
                {
                    Toast.makeText(HomeActivityMember.this," Aplikasi tidak mendapat izin lokasi ",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public void menuPesananMember()
    {
        Intent i = new Intent(HomeActivityMember.this, ManajemenPesananMember.class);
        startActivity(i);
    }

    public void menuAgenTerdekat()
    {
        Dexter.withContext(HomeActivityMember.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport)
            {
                if (multiplePermissionsReport.areAllPermissionsGranted())
                {
                    Intent i = new Intent(HomeActivityMember.this, AgenNearActivity.class);
                    startActivity(i);
                    Toast.makeText(HomeActivityMember.this, " Izin lokasi diberikan ",Toast.LENGTH_LONG).show();
                }

                // check for permanent denial of any permission
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied())
                {
                    Toast.makeText(HomeActivityMember.this," Aplikasi tidak mendapat izin lokasi ",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public void menuProfilMember()
    {
        Intent i = new Intent(HomeActivityMember.this, ProfilMemberActivity.class);
        startActivity(i);
    }

    private void menulaporan()
    {
        Intent i = new Intent(HomeActivityMember.this, ChartLaporanActivity.class);
        startActivity(i);
    }

    public void menuLogout()
    {
        FirebaseAuth.getInstance().signOut();

        stopService(new Intent(this, ListenOrderService.class));

        SessionManager session = new SessionManager(HomeActivityMember.this);
        session.logoutUser();

        Intent i = new Intent(HomeActivityMember.this, AuthActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        HomeActivityMember.this.finish();
        startActivity(i);
    }
}