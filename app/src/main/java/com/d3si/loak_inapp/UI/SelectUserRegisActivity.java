package com.d3si.loak_inapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Agen.RegisterActivityAgen;
import com.d3si.loak_inapp.UI.Member.RegisterActivityMember;

public class SelectUserRegisActivity extends AppCompatActivity {

    Button buttonRegisterMember, buttonRegisterAgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_regis);

        buttonRegisterAgen = findViewById(R.id.btn_register_agen);
        buttonRegisterMember = findViewById(R.id.btn_register_member);

        buttonRegisterMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserRegisActivity.this, RegisterActivityMember.class);
                startActivity(i);
            }
        });

        buttonRegisterAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserRegisActivity.this, RegisterActivityAgen.class);
                startActivity(i);
            }
        });
    }
}