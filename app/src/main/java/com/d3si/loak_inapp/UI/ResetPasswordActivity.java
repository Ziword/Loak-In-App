package com.d3si.loak_inapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.d3si.loak_inapp.Constructor.ConstLogic;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText resetPassEmail;
    Button btnResetPass;
    FirebaseAuth mAuth;

    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPassEmail = findViewById(R.id.emailResetPass);
        btnResetPass = findViewById(R.id.btn_resetPass);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = resetPassEmail.getText().toString().trim().toLowerCase();
                if(email.isEmpty()){
                    resetPassEmail.setError("Dibutuhkan Email");
                    resetPassEmail.requestFocus();
                    return;
                }
                DBConnector();
                Call<ConstLogic> call = db.checkUser(email);
                call.enqueue(new Callback<ConstLogic>() {
                    @Override
                    public void onResponse(Call<ConstLogic> call, Response<ConstLogic> response) {
                        if(response.body().getMESSAGE().equalsIgnoreCase("1"))
                        {
                            mAuth = FirebaseAuth.getInstance();
                            String email = resetPassEmail.getText().toString().trim().toLowerCase();
                            if(email.isEmpty()){
                                resetPassEmail.setError("Dibutuhkan Email");
                                resetPassEmail.requestFocus();
                                return;
                            }
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task)
                                {
                                    Toast.makeText(ResetPasswordActivity.this, "Password baru telah dikirim melalui email.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ResetPasswordActivity.this, AuthActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    ResetPasswordActivity.this.finish();
                                }
                            });
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "User Belum Terdaftar", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ConstLogic> call, Throwable t) {
                        Toast.makeText(ResetPasswordActivity.this, "Jaringan / Server error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void DBConnector()
    {

        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}