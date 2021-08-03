package com.d3si.loak_inapp.UI.Agen;

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

import com.d3si.loak_inapp.Module.SessionManager;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.AuthActivity;
import com.d3si.loak_inapp.UI.ChartLaporanActivity;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
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

public class HomeActivityAgen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView namaUser, jenisUser, keProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_agen);

//        NAVBAR
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
            public void onClick(View v)
            {
                menuProfilAgen();
            }
        });

        getUserNavbar();

//        NAVBAR

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    private void getUserNavbar()
    {
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
                    Toast.makeText(HomeActivityAgen.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void transaksiBeliAgen(View view)
    {
        menuTransaksiBeliAgen();
    }

    public void manajemenPesananAgen(View view)
    {
        menuManajemenPesananAgen();
    }

    public void riwayatTransaksi(View view) {
        menuRiwayatTransaksi();
    }

    public void profilAgen(View view)
    {
        menuProfilAgen();
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

    public void menuTransaksiBeliAgen()
    {
        Dexter.withContext(HomeActivityAgen.this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if(multiplePermissionsReport.areAllPermissionsGranted())
                {
                    Intent i = new Intent(HomeActivityAgen.this, TransaksiBeliAgenActivity.class);
                    startActivity(i);
                } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied())
                {
                    Toast.makeText(HomeActivityAgen.this," Aplikasi tidak mendapat izin lokasi ",Toast.LENGTH_LONG).show();
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

    public void menuManajemenPesananAgen()
    {
        Intent i = new Intent(HomeActivityAgen.this, ProsesOrderAgenActivity.class);
        startActivity(i);
    }

    public void menuRiwayatTransaksi()
    {
        Intent i = new Intent(HomeActivityAgen.this, RiwayatOrderAgenActivity.class);
        startActivity(i);
    }

    public void menuProfilAgen()
    {
        Intent i = new Intent(HomeActivityAgen.this, ProfilAgenActivity.class);
        startActivity(i);
    }

    public void menuLaporan()
    {
        Intent i = new Intent(HomeActivityAgen.this, ChartLaporanActivity.class);
        startActivity(i);
    }

    public void menuLogOut()
    {
        SessionManager session = new SessionManager(HomeActivityAgen.this);
        session.logoutUser();

        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(HomeActivityAgen.this, AuthActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        HomeActivityAgen.this.finish();
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_loakinMap:
                menuTransaksiBeliAgen();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_agenPesanan:
                menuManajemenPesananAgen();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_agenRiwayatTransaksi:
                menuRiwayatTransaksi();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_agenLogout:
                menuLogOut();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case R.id.nav_laporan:
                menuLaporan();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }
}