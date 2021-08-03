package com.d3si.loak_inapp.UI.Agen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiAgen;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.NotificationCaller;
import com.d3si.loak_inapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailBarangAgenActivity extends AppCompatActivity
{
    LinearLayout imgBarang;
    TextView tv_JT, tv_nBarang, tv_jBerat, tv_namaPenjual, tv_alamatBarang, tv_ketTransaksi;
    Button btnKonfirmasi;

    MyDB db;
    BottomSheetDialog bottomSheetDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi");

    ConstAdapterTransaksiAgen obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_agen);

        obj = getIntent().getParcelableExtra("BARANG");

        tv_JT = findViewById(R.id.tv_JT);
        tv_nBarang = findViewById(R.id.tv_nBarang);
        tv_jBerat = findViewById(R.id.tv_jBerat);
        tv_namaPenjual = findViewById(R.id.tv_namaPenjual);
        tv_alamatBarang = findViewById(R.id.tv_alamatBarang);
        tv_ketTransaksi = findViewById(R.id.tv_ketTransaksi);

        imgBarang = findViewById(R.id.imgBarang);

        tv_JT.setText(obj.getJENIS_BARANG());
        tv_nBarang.setText(obj.getNAMA_BARANG_LOAK());
        tv_jBerat.setText(String.valueOf(obj.getBERAT_BARANG())+" Kg");
        tv_namaPenjual.setText("Nama Penjual : "+obj.getNAMA_LENGKAP_MEMBER());
        tv_alamatBarang.setText(obj.getALAMAT_TRANSAKSI());
        tv_ketTransaksi.setText(obj.getKETERANGAN_TRANSAKSI());
        System.out.println(obj.getGambarBarang());
        Glide.with(this).load(obj.getGambarBarang()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imgBarang.setBackground(resource);
                }
            }
        });

        btnKonfirmasi = findViewById(R.id.btnKonfirmasi);
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bottomSheetDialog = new BottomSheetDialog(DetailBarangAgenActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(DetailBarangAgenActivity.this).inflate(R.layout.layout_bs_detail_barang_agen, null);

                if(obj.getNAMA_STATUS_TRANSAKSI().equalsIgnoreCase("Melakukan Penjemputan Barang")) {
                    LinearLayout linearLayoutBtnAgen = bottomSheetView.findViewById(R.id.linearAgen);
                    linearLayoutBtnAgen.setVisibility(View.VISIBLE);
                    Button konfirmasi_selesaiAgen = bottomSheetView.findViewById(R.id.konfirmasi_selesaiAgen);
                    konfirmasi_selesaiAgen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(DetailBarangAgenActivity.this);
                            dialog.setTitle("Konfirmasi Pengambilan Barang").setMessage("Pastikan anda telah berada pada tempat penjual.").setCancelable(true).setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    konfirmasiSelesai(obj.getID_TRANSAKSI(), obj.getNAMA_BARANG_LOAK());
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                    });


                    Button konfirmasi_batalAgen = bottomSheetView.findViewById(R.id.konfirmasi_batalAgen);
                    konfirmasi_batalAgen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(DetailBarangAgenActivity.this);
                            dialog.setTitle("Konfirmasi Pembatalan Barang").setMessage("Apakah anda yakin akan membatalkan pesanan ini ?").setCancelable(true).setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    batalkanOrder(obj.getID_TRANSAKSI(), obj.getNAMA_BARANG_LOAK());
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                    });
                }

                TextView idBarang = bottomSheetView.findViewById(R.id.row_detail_id_barang);
                TextView beratBarang = bottomSheetView.findViewById(R.id.row_detail_berat_barang);
                TextView namaPenjual = bottomSheetView.findViewById(R.id.row_detail_nama_penjual);
                TextView namaBarang = bottomSheetView.findViewById(R.id.row_detail_nama_barang);
                TextView jenisBarang = bottomSheetView.findViewById(R.id.row_detail_jenis_loak);
                TextView catatanBarang = bottomSheetView.findViewById(R.id.row_detail_catatan_barang);
                TextView alamatBarang = bottomSheetView.findViewById(R.id.row_detail_alamat_barang);
                LinearLayout btnGmap = bottomSheetView.findViewById(R.id.btn_gmap);
                LinearLayout btnWa = bottomSheetView.findViewById(R.id.btn_wa);
                btnGmap.setVisibility(View.GONE);
                btnWa.setVisibility(View.GONE);

                idBarang.setText(obj.getID_TRANSAKSI());
                namaBarang.setText("Nama Loak : "+obj.getNAMA_BARANG_LOAK());
                jenisBarang.setText("Jenis Loak : "+obj.getJENIS_BARANG());
                beratBarang.setText(obj.getBERAT_BARANG()+" Kg");
                catatanBarang.setText(obj.getKETERANGAN_TRANSAKSI());
                alamatBarang.setText(obj.getALAMAT_TRANSAKSI());
                namaPenjual.setVisibility(View.VISIBLE);
                namaPenjual.setText("Nama Penjual : "+obj.getNAMA_LENGKAP_MEMBER());
                btnGmap.setVisibility(View.VISIBLE);
                btnGmap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double latMarker = Double.parseDouble(obj.getLAT_MEMBER());
                        Double longMarker = Double.parseDouble(obj.getLONG_MEMBER());
                        openGmap(latMarker, longMarker);
                    }
                });
                btnWa.setVisibility(View.VISIBLE);
                btnWa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openWa(obj.getNO_TELP_MEMBER());
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void konfirmasiSelesai(String id_transaksi, String nama_barang_loak)
    {
        String uidAgen = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("User").child(uidAgen).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String id_agen = dataSnapshot.child("ID_AGEN").getValue().toString();
                databaseReference.child(id_transaksi).child("ID_AGEN").setValue(id_agen);
                databaseReference.child(id_transaksi).child("ID_STATUS_TRANSAKSI").setValue("ST4");
                //  Ubah data db

                DBBuilder();
                Call<ResponseBody> call = db.updateTransaksi(id_transaksi, id_agen, "ST4");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i = new Intent(DetailBarangAgenActivity.this, HomeActivityAgen.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        bottomSheetDialog.dismiss();
                        NotificationCaller notificationCaller = new NotificationCaller(DetailBarangAgenActivity.this);
                        notificationCaller.notifOrderSelesai();
                        DetailBarangAgenActivity.this.startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void batalkanOrder(String id_transaksi, String nama_barang_loak)
    {

//        Ubah data pada firebase
        databaseReference.child(id_transaksi).child("ID_AGEN").setValue("");
        databaseReference.child(id_transaksi).child("ID_STATUS_TRANSAKSI").setValue("ST1");
//        Ubah data db
        DBBuilder();
        Call<ResponseBody> call = db.cancelTransaksi(id_transaksi,"ST1");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                NotificationCaller notificationCaller = new NotificationCaller(DetailBarangAgenActivity.this);
                notificationCaller.notifDeleteLoak(nama_barang_loak);
                Intent i = new Intent(DetailBarangAgenActivity.this, HomeActivityAgen.class);
                bottomSheetDialog.dismiss();

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                DetailBarangAgenActivity.this.startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void openWa(String no_telp_member)
    {
        Uri uri = Uri.parse("smsto:" + no_telp_member);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        DetailBarangAgenActivity.this.startActivity(Intent.createChooser(i, "Aplikasi LoakIn"));
    }

    private void openGmap(Double latMarker, Double longMarker)
    {
        Uri navigationIntentUri = Uri.parse("google.navigation:q=" + latMarker + "," + longMarker);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        DetailBarangAgenActivity.this.startActivity(mapIntent);
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}