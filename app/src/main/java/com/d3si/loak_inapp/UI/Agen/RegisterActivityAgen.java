package com.d3si.loak_inapp.UI.Agen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.d3si.loak_inapp.UI.AuthActivity;
import com.d3si.loak_inapp.Constructor.ConstAgen;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivityAgen extends AppCompatActivity {
    EditText regEmailAgen, regPassAgen;
    Button btnRegAgen, kelogin;
    private FirebaseAuth mAuth;
    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agen);
        regEmailAgen = findViewById(R.id.regEmailAgen);
        regPassAgen = findViewById(R.id.regPasswordAgen);

        mAuth = FirebaseAuth.getInstance();

        DBBuilder();

        btnRegAgen = findViewById(R.id.btnSignUpAgen);
        btnRegAgen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = regEmailAgen.getText().toString().trim().toLowerCase();
                String password = regPassAgen.getText().toString().trim();
                if(email.isEmpty()){
                    regEmailAgen.setError("Dibutuhkan Email");
                    regEmailAgen.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    regPassAgen.setError("Dibutuhkan Password");
                    regPassAgen.requestFocus();
                    return;
                }
                cekDaftarAgen(email, password);
            }
        });

        kelogin = findViewById(R.id.ke_login_agen);
        kelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivityAgen.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                RegisterActivityAgen.this.finish();
                startActivity(i);
            }
        });

    }

    private void cekDaftarAgen(String email, String pass)
    {
        ConstAgen constAgen = new ConstAgen();
        constAgen.setEMAIL_AGEN(email);
        Call<ConstAgen> call = db.cekDaftarAgen(constAgen.getEMAIL_AGEN());
        call.enqueue(new Callback<ConstAgen>() {
            @Override
            public void onResponse(Call<ConstAgen> call, Response<ConstAgen> response)
            {
                System.out.println("RESPON SERVER : "+response.body().getMESSAGE());
                if(response.body().getMESSAGE().equalsIgnoreCase("1"))
                {
                    Toast.makeText(RegisterActivityAgen.this, "Pendaftaran Sedang di Proses",Toast.LENGTH_LONG).show();
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        ConstAgen user = new ConstAgen(response.body().getID_AGEN(),response.body().getID_JENIS_USER(),response.body().getNAMA_AGEN(),response.body().getEMAIL_AGEN(), FirebaseAuth.getInstance().getCurrentUser().getUid(),response.body().getNO_TELP_AGEN(), response.body().getALAMAT_LENGKAP_AGEN(),response.body().getLONGTITUDE(),response.body().getLATITUDE(),response.body().getTANGGAL_DAFTAR_AGEN());
                                        updateUIDAgen(response.body().getEMAIL_AGEN(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(RegisterActivityAgen.this, "Pendaftaran Berhasil!", Toast.LENGTH_LONG).show();
                                                    doLogout();
                                                    //Redirect to login
                                                    Intent i = new Intent(RegisterActivityAgen.this, AuthActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    RegisterActivityAgen.this.finish();
                                                    startActivity(i);
                                                } else {
                                                    Toast.makeText(RegisterActivityAgen.this, "Pendaftaran Gagal, Data Tidak Valid !", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterActivityAgen.this, "Pendaftaran Gagal, Kendala Database !", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivityAgen.this, "Silahkan Mendaftar ke Cabang Terdekat Loak-In",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ConstAgen> call, Throwable t)
            {
                System.out.println("RESPON SERVER : "+t);
            }
        });

    }

    private void updateUIDAgen(String email_agen, String uid)
    {
        Call<ResponseBody> call = db.updateAgenUserID(email_agen, uid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("UPDATE BERHASIL");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void doLogout()
    {
        FirebaseAuth.getInstance().signOut();
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}