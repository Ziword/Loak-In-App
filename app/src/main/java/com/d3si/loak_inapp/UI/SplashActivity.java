package com.d3si.loak_inapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.d3si.loak_inapp.Module.ListenOrderService;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Agen.HomeActivityAgen;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    for(int w=0; w<=progressBar.getMax(); w++){
                        progressBar.setProgress(w);
                        Thread.sleep(30);
                        if(w == 25)
                        {
                            getSession();
                        }
                    }
                }
                catch(InterruptedException ex){
                    System.out.println("Error Splash : "+ex);
                }
                finally
                {
                }
            }
        });
        thread.start();

    }

    private void getSession()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null)
        {
            String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("User").child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(Task<DataSnapshot> task)
                {
                    try {
                        String JU;
                        JU = task.getResult().child("ID_JENIS_USER").getValue().toString();
                        if (JU.equalsIgnoreCase("U2")) {
                            Intent i = new Intent(SplashActivity.this, HomeActivityMember.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            SplashActivity.this.finish();
                        } else if (JU.equalsIgnoreCase("U3")) {
                            Intent i = new Intent(SplashActivity.this, HomeActivityAgen.class);
                            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            SplashActivity.this.finish();
                        }
                    } catch (Exception e)
                    {
                        Toast.makeText(SplashActivity.this,"Gagal Kendala Jaringan",Toast.LENGTH_LONG).show();
                        SplashActivity.this.finish();
                    }
                }
            });
        } else {
            Intent i = new Intent(SplashActivity.this, AuthActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            SplashActivity.this.finish();
        }
    }
}