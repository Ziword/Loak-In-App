package com.d3si.loak_inapp.Adapter.Member;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.R;
import com.d3si.loak_inapp.UI.Member.DetailBarangMemberActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.util.ArrayList;

public class AdapterSelesaiTransaksiMember extends RecyclerView.Adapter<AdapterSelesaiTransaksiMember.MyViewHolder>{
    private ArrayList<ConstAdapterTransaksiMember> items;
    private Context context;
    BottomSheetDialog bottomSheetDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView row_selesai_idtransaksi, row_selesai_nama_barang, row_selesai_berat_barang, row_selesai_jenis_barang, row_selesai_status_transaksi, row_selesai_tanggal_transaksi, row_selesai_bulan_transaksi, row_selesai_tahun_transaksi;
        private LinearLayout colorStatusTransaksi;
        public MyViewHolder(View itemView) {
            super(itemView);

            row_selesai_idtransaksi = itemView.findViewById(R.id.row_selesai_idtransaksi);
            row_selesai_nama_barang = itemView.findViewById(R.id.row_selesai_nama_barang);
            row_selesai_berat_barang= itemView.findViewById(R.id.row_selesai_berat_barang);
            row_selesai_jenis_barang= itemView.findViewById(R.id.row_selesai_jenis_barang);
            row_selesai_status_transaksi= itemView.findViewById(R.id.row_selesai_status_transaksi);
            
            row_selesai_tanggal_transaksi=itemView.findViewById(R.id.row_selesai_tanggal_transaksi);
            row_selesai_bulan_transaksi= itemView.findViewById(R.id.row_selesai_bulan_transaksi);
            row_selesai_tahun_transaksi= itemView.findViewById(R.id.row_selesai_tahun_transaksi);

            colorStatusTransaksi = itemView.findViewById(R.id.colorStatusTransaksi);
        }
    }

    public AdapterSelesaiTransaksiMember(ArrayList<ConstAdapterTransaksiMember> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(ArrayList<ConstAdapterTransaksiMember> items) {
        this.items = items;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selesai_transaksi_member,parent, false);
        return new AdapterSelesaiTransaksiMember.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSelesaiTransaksiMember.MyViewHolder holder, int position) {
        ConstAdapterTransaksiMember obj = items.get(position);
        holder.row_selesai_idtransaksi.setText(obj.getID_TRANSAKSI());
        holder.row_selesai_nama_barang.setText(obj.getNAMA_BARANG_LOAK());
        holder.row_selesai_berat_barang.setText("Berat Barang : "+obj.getBERAT_BARANG()+ " Kg");
        holder.row_selesai_jenis_barang.setText("Jenis Transaksi Barang : "+obj.getJENIS_BARANG());
        holder.row_selesai_status_transaksi.setText(obj.getNAMA_STATUS_TRANSAKSI());

        if(obj.getNAMA_STATUS_TRANSAKSI().equalsIgnoreCase("Order Dibatalkan"))
        {
            holder.colorStatusTransaksi.setBackgroundColor(ContextCompat.getColor(context,R.color.redbtn));
        }

        try {
            holder.row_selesai_tanggal_transaksi.setText(obj.getTanggal());
            holder.row_selesai_bulan_transaksi.setText(obj.getBulan());
            holder.row_selesai_tahun_transaksi.setText(obj.getTahun());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailBarangMemberActivity.class);
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
