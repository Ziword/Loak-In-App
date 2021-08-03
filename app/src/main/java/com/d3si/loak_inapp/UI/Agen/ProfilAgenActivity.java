package com.d3si.loak_inapp.UI.Agen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d3si.loak_inapp.Constructor.ConstProfilAgen;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilAgenActivity extends AppCompatActivity {
    MyDB db;
    FirebaseAuth mAuth;
    TextView tv_profil_nama_agen, tv_barang_buy_agen, tv_email_agen, tv_telp_agen, tv_alamat_agen, tv_tanggal_daftar_agen;
    TextView backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_agen);

        tv_profil_nama_agen = findViewById(R.id.tv_profil_nama_agen);
        tv_barang_buy_agen = findViewById(R.id.tv_barang_buy_agen);
        tv_email_agen = findViewById(R.id.tv_email_agen);
        tv_telp_agen = findViewById(R.id.tv_telp_agen);
        tv_alamat_agen = findViewById(R.id.tv_alamat_agen);
        tv_tanggal_daftar_agen = findViewById(R.id.tv_tanggal_daftar_agen);

        String UID = mAuth.getInstance().getCurrentUser().getUid();

        DBBuilder();
        Call<ConstProfilAgen> call = db.getProfilAgen(UID);
        call.enqueue(new Callback<ConstProfilAgen>() {
            @Override
            public void onResponse(Call<ConstProfilAgen> call, Response<ConstProfilAgen> response) {
                tv_profil_nama_agen.setText(response.body().getNAMA());
                tv_barang_buy_agen.setText(response.body().getTOTAL_ORDER());
                tv_email_agen.setText(response.body().getEMAIL());
                tv_telp_agen.setText(response.body().getTELP());
                tv_alamat_agen.setText(response.body().getALAMAT());
                tv_tanggal_daftar_agen.setText(response.body().getTANGGAL_DAFTAR());
            }

            @Override
            public void onFailure(Call<ConstProfilAgen> call, Throwable t) {

            }
        });

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}