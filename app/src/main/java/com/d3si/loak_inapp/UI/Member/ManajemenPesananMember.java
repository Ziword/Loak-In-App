package com.d3si.loak_inapp.UI.Member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d3si.loak_inapp.R;

import org.w3c.dom.Text;

public class ManajemenPesananMember extends AppCompatActivity {

    LinearLayout linearbttnTransaksiSelesai, linearbttnTransaksiDiproses;

    TextView backMenu;

    Button bttnTransaksiDiproses,bttnTransaksiSelesai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_pesanan_member);

        linearbttnTransaksiDiproses = findViewById(R.id.linearbttnTransaksiDiproses);
        linearbttnTransaksiSelesai = findViewById(R.id.linearbttnTransaksiSelesai);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.containerManajemenPesananMember, new FragProsesTransaksiMember()).commit();

        bttnTransaksiDiproses = findViewById(R.id.bttnTransaksiDiproses);
        bttnTransaksiSelesai = findViewById(R.id.bttnTransaksiSelesai);

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bttnTransaksiDiproses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.containerManajemenPesananMember, new FragProsesTransaksiMember()).commit();

            }
        });

        bttnTransaksiSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.containerManajemenPesananMember, new FragSelesaiTransaksiMember()).commit();
            }
        });
    }
}