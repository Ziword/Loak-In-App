package com.d3si.loak_inapp.UI.Member;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.d3si.loak_inapp.Constructor.ConstLogic;
import com.d3si.loak_inapp.UI.AuthActivity;
import com.d3si.loak_inapp.Constructor.ConstMember;
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

public class RegisterActivityMember extends AppCompatActivity {

    EditText regisNama, regisEmail, regisTelpon, regisPassword;
    RadioButton rbJK;
    RadioGroup rgJk;
    Button btnSignup, btnLogin;
    MyDB db;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        mAuth = FirebaseAuth.getInstance();

        regisNama = findViewById(R.id.reg_nama);
        regisEmail = findViewById(R.id.reg_email);
        regisTelpon = findViewById(R.id.reg_phone_number);
        regisPassword = findViewById(R.id.reg_password);

        rgJk = findViewById(R.id.rgJk);
        DBBuilder();

        btnLogin = findViewById(R.id.ke_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivityMember.this, AuthActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        btnSignup = findViewById(R.id.btn_SignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser()
    {
        String nama = regisNama.getText().toString().trim();
        String email = regisEmail.getText().toString().trim();
        String telpon = regisTelpon.getText().toString().trim();
        String password = regisPassword.getText().toString().trim();



        if (rgJk.getCheckedRadioButtonId() == -1)
        {
            // no radio buttons are checked
            Toast.makeText(this, "jenis kelamin harus dipilih", Toast.LENGTH_LONG).show();
            rgJk.requestFocus();
            return;
        }
        else
        {
            // get selected radio button from radioGroup
            int selectedId = rgJk.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            rbJK = (RadioButton)findViewById(selectedId);
        }

        String jenkel = rbJK.getText().toString();

        if(nama.isEmpty()){
            regisNama.setError("Dibutuhkan Nama Lengkap");
            regisNama.requestFocus();
            return;
        }
        if(email.isEmpty()){
            regisEmail.setError("Dibutuhkan Email");
            regisEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            regisEmail.setError("Masukkan Valid Email");
            regisEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            regisPassword.setError("Dibutuhkan Password");
            regisPassword.requestFocus();
            return;
        }

        if(telpon.isEmpty()){
            regisTelpon.setError("Dibutuhkan Nomor Telpon");
            regisTelpon.requestFocus();
            return;
        }

        Call<ConstLogic> call = db.checkUser(email);
        call.enqueue(new Callback<ConstLogic>() {
            @Override
            public void onResponse(Call<ConstLogic> call, Response<ConstLogic> response) {
                if(response.body().getMESSAGE().equalsIgnoreCase("1"))
                {
                    Toast.makeText(RegisterActivityMember.this, "Email Telah Terdaftar", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        ConstMember user = new ConstMember(FirebaseAuth.getInstance().getCurrentUser().getUid(), "U2",nama,email,jenkel,telpon);
                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Call<ResponseBody> call = db.daftarMember(FirebaseAuth.getInstance().getCurrentUser().getUid(),"U2",nama,email,jenkel,telpon);
                                                    call.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                            System.out.println("RESPONSE SERVER : " + t);
                                                        }
                                                    });

                                                    Toast.makeText(RegisterActivityMember.this, "Pendaftaran Berhasil!", Toast.LENGTH_LONG).show();
                                                    doLogout();
                                                    //Redirect to login
                                                    Intent i = new Intent(RegisterActivityMember.this, AuthActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    RegisterActivityMember.this.finish();
                                                    startActivity(i);
                                                } else {
                                                    Toast.makeText(RegisterActivityMember.this, "Pendaftaran Gagal, Data Tidak Valid !", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterActivityMember.this, "Pendaftaran Gagal, Kendala Database !", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<ConstLogic> call, Throwable t) {
                Toast.makeText(RegisterActivityMember.this, "Pendaftaran Gagal, Kendala Database SQL!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }

    private void doLogout()
    {
        FirebaseAuth.getInstance().signOut();
    }
}