package com.d3si.loak_inapp.UI.Member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.NotificationCaller;
import com.d3si.loak_inapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailBarangMemberActivity extends AppCompatActivity {
    LinearLayout imgBarang;
    TextView tv_JT, tv_nBarang, tv_jBerat, tv_statusTransaksi, tv_alamatBarang, tv_ketTransaksi;
    Button btnKonfirmasi;

    TextView tv_back;

    BottomSheetDialog bottomSheetDialog;
    MyDB db;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi");

    ConstAdapterTransaksiMember obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_member);

        obj = getIntent().getParcelableExtra("BARANG");

        tv_JT = findViewById(R.id.tv_JT);
        tv_nBarang = findViewById(R.id.tv_nBarang);
        tv_jBerat = findViewById(R.id.tv_jBerat);
        tv_statusTransaksi = findViewById(R.id.tv_statusTransaksi);
        tv_alamatBarang = findViewById(R.id.tv_alamatBarang);
        tv_ketTransaksi = findViewById(R.id.tv_ketTransaksi);

        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgBarang = findViewById(R.id.imgBarang);

        tv_JT.setText(obj.getJENIS_BARANG());
        tv_nBarang.setText(obj.getNAMA_BARANG_LOAK());
        tv_jBerat.setText(String.valueOf(obj.getBERAT_BARANG())+" Kg");
        tv_statusTransaksi.setText("Status Transaksi : "+obj.getNAMA_STATUS_TRANSAKSI());
        tv_alamatBarang.setText(obj.getALAMAT_TRANSAKSI());
        tv_ketTransaksi.setText(obj.getKETERANGAN_TRANSAKSI());

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
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(DetailBarangMemberActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(DetailBarangMemberActivity.this).inflate(R.layout.layout_bs_detail_barang_agen, null);
                Button btnCancel = bottomSheetView.findViewById(R.id.btn_cancel_loak);

                if(obj.getNAMA_STATUS_TRANSAKSI().equalsIgnoreCase("Menunggu Agen")) {
                    btnCancel.setVisibility(View.VISIBLE);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(DetailBarangMemberActivity.this);
                            dialog.setTitle("Konfirmasi PembatalanPesanan").setMessage("Apakah anda yakin akan membatalkan pesanan ini?").
                                    setCancelable(true).setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
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

                LinearLayout statusBatal = bottomSheetView.findViewById(R.id.statusTransaksiBatal);
                LinearLayout statusSelesai = bottomSheetView.findViewById(R.id.statusTransaksiSelesai);

                LinearLayout btnGmap = bottomSheetView.findViewById(R.id.btn_gmap);
                LinearLayout btnWa = bottomSheetView.findViewById(R.id.btn_wa);
                btnGmap.setVisibility(View.GONE);
                btnWa.setVisibility(View.GONE);

                if(obj.getNAMA_STATUS_TRANSAKSI().equalsIgnoreCase("Order Dibatalkan"))
                {
                    statusBatal.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.GONE);
                } else if(obj.getNAMA_STATUS_TRANSAKSI().equalsIgnoreCase("Order Selesai")) {
                    statusSelesai.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.GONE);
                }

                idBarang.setText(obj.getID_TRANSAKSI());
                namaBarang.setText("Nama Loak : "+obj.getNAMA_BARANG_LOAK());
                jenisBarang.setText("Jenis Loak : "+obj.getJENIS_BARANG());
                beratBarang.setText(obj.getBERAT_BARANG()+" Kg");
                catatanBarang.setText(obj.getKETERANGAN_TRANSAKSI());
                alamatBarang.setText(obj.getALAMAT_TRANSAKSI());
                namaPenjual.setVisibility(View.GONE);
                btnGmap.setVisibility(View.GONE);
                btnWa.setVisibility(View.GONE);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void batalkanOrder(String id_transaksi, String nama_barang)
    {
//        Ubah data pada firebase
        databaseReference.child(id_transaksi).child("ID_AGEN").setValue("");
        databaseReference.child(id_transaksi).child("ID_STATUS_TRANSAKSI").setValue("ST3");
//        Ubah data db
        DBBuilder();
        Call<ResponseBody> call = db.cancelTransaksi(id_transaksi,"ST3");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                NotificationCaller notificationCaller = new NotificationCaller(getApplication());
                notificationCaller.notifDeleteLoak(nama_barang);
                Intent i = new Intent(DetailBarangMemberActivity.this, HomeActivityMember.class);
                bottomSheetDialog.dismiss();

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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