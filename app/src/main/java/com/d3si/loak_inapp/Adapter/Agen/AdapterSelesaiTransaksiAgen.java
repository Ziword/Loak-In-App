package com.d3si.loak_inapp.Adapter.Agen;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiAgen;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;

public class AdapterSelesaiTransaksiAgen extends RecyclerView.Adapter<AdapterSelesaiTransaksiAgen.MyViewHolder>{

    private ArrayList<ConstAdapterTransaksiAgen> items;
    private Context context;
    MyDB db;
    BottomSheetDialog bottomSheetDialog;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transaksi");
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView row_IdTransaksi, row_NamaBarang, row_StatusTransaksi, row_JenisBarang, row_TanggalTransaksi, row_BulanTransaksi, row_TahunTransaksi, row_BeratBarang;
        private LinearLayout stsusBarang;

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
            stsusBarang = itemView.findViewById(R.id.stsusBarang);
        }
    }

    public AdapterSelesaiTransaksiAgen(ArrayList<ConstAdapterTransaksiAgen> items, Context context) {
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
    public AdapterSelesaiTransaksiAgen.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_proses_transaksi_member,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSelesaiTransaksiAgen.MyViewHolder holder, int position) {
        ConstAdapterTransaksiAgen obj = items.get(position);

        holder.stsusBarang.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
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
                bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.layout_bs_detail_barang_agen, null);

                TextView idBarang = bottomSheetView.findViewById(R.id.row_detail_id_barang);
                TextView beratBarang = bottomSheetView.findViewById(R.id.row_detail_berat_barang);
                TextView namaPenjual = bottomSheetView.findViewById(R.id.row_detail_nama_penjual);
                TextView namaBarang = bottomSheetView.findViewById(R.id.row_detail_nama_barang);
                TextView jenisBarang = bottomSheetView.findViewById(R.id.row_detail_jenis_loak);
                TextView catatanBarang = bottomSheetView.findViewById(R.id.row_detail_catatan_barang);
                TextView alamatBarang = bottomSheetView.findViewById(R.id.row_detail_alamat_barang);
                LinearLayout btnGmap = bottomSheetView.findViewById(R.id.btn_gmap);
                LinearLayout btnWa = bottomSheetView.findViewById(R.id.btn_wa);
                LinearLayout status = bottomSheetView.findViewById(R.id.statusTransaksiSelesai);

                idBarang.setText(obj.getID_TRANSAKSI());
                namaBarang.setText("Nama Loak : "+obj.getNAMA_BARANG_LOAK());
                jenisBarang.setText("Jenis Loak : "+obj.getJENIS_BARANG());
                beratBarang.setText(obj.getBERAT_BARANG()+" Kg");
                catatanBarang.setText(obj.getKETERANGAN_TRANSAKSI());
                alamatBarang.setText(obj.getALAMAT_TRANSAKSI());
                namaPenjual.setVisibility(View.VISIBLE);
                namaPenjual.setText("Nama Penjual : "+obj.getNAMA_LENGKAP_MEMBER());
                btnGmap.setVisibility(View.GONE);
                btnWa.setVisibility(View.GONE);
                status.setVisibility(View.VISIBLE);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
