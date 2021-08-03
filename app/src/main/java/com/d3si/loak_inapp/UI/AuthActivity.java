package com.d3si.loak_inapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.d3si.loak_inapp.Module.SessionManager;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Agen.HomeActivityAgen;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class AuthActivity extends AppCompatActivity {
//    Deklarasi Variabel Komponen
    EditText loginEmail, loginPassword;
    Button btnSignup, btnLogin, btnLupaPass;

    SessionManager session;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        session = new SessionManager(AuthActivity.this);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();

        btnSignup = findViewById(R.id.ke_daftar);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthActivity.this, SelectUserRegisActivity.class);
                startActivity(i);
            }
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        btnLupaPass = findViewById(R.id.ke_lupaPass);
        btnLupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthActivity.this, ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void userLogin()
    {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if(email.isEmpty()){
            loginEmail.setError("Dibutuhkan Email");
            loginEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            loginPassword.setError("Dibutuhkan Password");
            loginPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            loginEmail.setError("Masukkan Valid Email");
            loginEmail.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        String UID =user.getUid();
                        FirebaseDatabase.getInstance().getReference("User").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(Task<DataSnapshot> task) {
                                try{
                                    String JU = task.getResult().child("ID_JENIS_USER").getValue().toString();
                                    if(JU.equalsIgnoreCase("U2"))
                                    {
                                        String namaMember = task.getResult().child("NAMA_LENGKAP_MEMBER").getValue().toString();
                                        String emailMember = task.getResult().child("EMAIL_MEMBER").getValue().toString();
                                        session.createLoginSession(UID, JU, emailMember, namaMember);

                                        Intent i = new Intent(AuthActivity.this, HomeActivityMember.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        AuthActivity.this.finish();
                                        AuthActivity.this.startActivity(i);
                                    } else if (JU.equalsIgnoreCase("U3")) {
                                        String idAgen = task.getResult().child("ID_AGEN").getValue().toString();
                                        String namaAgen = task.getResult().child("NAMA_AGEN").getValue().toString();
                                        String emailAgen = task.getResult().child("EMAIL_AGEN").getValue().toString();
                                        session.createLoginSession(idAgen, JU, emailAgen,namaAgen);

                                        Intent i = new Intent(AuthActivity.this, HomeActivityAgen.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        AuthActivity.this.finish();
                                        AuthActivity.this.startActivity(i);
                                    }
                                } catch (Exception e)
                                {
                                    Toast.makeText(AuthActivity.this, "Koneksi Internet Gagal", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(AuthActivity.this, "Periksa Email, untuk memverifikasi akun !", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(AuthActivity.this, "Login Gagal, Mohon Cek Data Login Kembali", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}