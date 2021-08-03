package com.d3si.loak_inapp.Adapter.Agen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiAgen;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.NotificationCaller;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Agen.DetailBarangAgenActivity;
import com.d3si.loak_inapp.UI.Agen.HomeActivityAgen;
import com.d3si.loak_inapp.UI.Agen.TransaksiBeliAgenActivity;
import com.d3si.loak_inapp.UI.Member.DetailBarangMemberActivity;
import com.d3si.loak_inapp.UI.Member.HomeActivityMember;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterProsesTransaksiAgen extends RecyclerView.Adapter<AdapterProsesTransaksiAgen.MyViewHolder> {

    private ArrayList<ConstAdapterTransaksiAgen> items;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView row_IdTransaksi, row_NamaBarang, row_StatusTransaksi, row_JenisBarang, row_TanggalTransaksi, row_BulanTransaksi, row_TahunTransaksi, row_BeratBarang;

        public MyViewHolder(View itemView) {
            super(itemView);

            row_IdTransaksi = itemView.findViewById(R.id.row_idtransaksi);
            row_NamaBarang = itemView.findViewById(R.id.row_namabarang);
            row_StatusTransaksi = itemView.findViewById(R.id.row_statusbarang);
            row_JenisBarang = itemView.findViewById(R.id.row_jenisbarang);
            row_BeratBarang = itemView.findViewById(R.id.row_beratbarang);
            row_TanggalTransaksi = itemView.findViewById(R.id.row_tanggal);
            row_BulanTransaksi = itemView.findViewById(R.id.row_bulan);
            row_TahunTransaksi = itemView.findViewById(R.id.row_tahun);
        }
    }

    public AdapterProsesTransaksiAgen(ArrayList<ConstAdapterTransaksiAgen> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(ArrayList<ConstAdapterTransaksiAgen> items) {
        this.items = items;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public AdapterProsesTransaksiAgen.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_proses_transaksi_member,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterProsesTransaksiAgen.MyViewHolder holder, int position) {
        ConstAdapterTransaksiAgen obj = items.get(position);

        holder.row_IdTransaksi.setText(obj.getID_TRANSAKSI());
        holder.row_NamaBarang.setText(obj.getNAMA_BARANG_LOAK());
        holder.row_StatusTransaksi.setText(obj.getNAMA_STATUS_TRANSAKSI());
        holder.row_BeratBarang.setText("Berat Barang : "+obj.getBERAT_BARANG() +" Kg");
        holder.row_JenisBarang.setText("Jenis Transaksi : "+obj.getJENIS_BARANG());
        try {
            holder.row_TanggalTransaksi.setText(obj.getTanggal());
            holder.row_BulanTransaksi.setText(obj.getBulan());
            holder.row_TahunTransaksi.setText(obj.getTahun());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(context, DetailBarangAgenActivity.class);
                i.putExtra("BARANG", obj);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
