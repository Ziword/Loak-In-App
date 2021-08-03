package com.d3si.loak_inapp.UI.Member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d3si.loak_inapp.Constructor.ConstProfilMember;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilMemberActivity extends AppCompatActivity {
    MyDB db;
    FirebaseAuth mAuth;
    TextView tv_profil_nama_member, tv_iklan_member, tv_barang_sell_member, tv_email_member, tv_no_member, tv_jk_member, tv_daftar_member;
    TextView backMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_member);

        tv_profil_nama_member = findViewById(R.id.tv_profil_nama_member);
        tv_iklan_member = findViewById(R.id.tv_iklan_member);
        tv_barang_sell_member = findViewById(R.id.tv_barang_sell_member);
        tv_email_member = findViewById(R.id.tv_email_member);
        tv_no_member = findViewById(R.id.tv_no_member);
        tv_jk_member = findViewById(R.id.tv_jk_member);
        tv_daftar_member = findViewById(R.id.tv_daftar_member);

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        String UID = mAuth.getInstance().getCurrentUser().getUid();

        DBBuilder();
        Call<ConstProfilMember> call = db.getProfilMember(UID);
        call.enqueue(new Callback<ConstProfilMember>() {
            @Override
            public void onResponse(Call<ConstProfilMember> call, Response<ConstProfilMember> response) {
                tv_profil_nama_member.setText(response.body().getNAMA());
                tv_iklan_member.setText(response.body().getTOTAL_ORDER());
                tv_barang_sell_member.setText(response.body().getORDER_BERHASIL());
                tv_email_member.setText(response.body().getEMAIL());
                tv_no_member.setText(response.body().getTELP());
                tv_jk_member.setText(response.body().getJK());
                tv_daftar_member.setText(response.body().getTGL_DAFTAR());
            }

            @Override
            public void onFailure(Call<ConstProfilMember> call, Throwable t) {

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