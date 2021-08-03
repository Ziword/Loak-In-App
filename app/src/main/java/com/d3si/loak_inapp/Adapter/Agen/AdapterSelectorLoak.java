package com.d3si.loak_inapp.Adapter.Agen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.d3si.loak_inapp.Constructor.ConstTransaksi;
import com.d3si.loak_inapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AdapterSelectorLoak extends RecyclerView.Adapter<AdapterSelectorLoak.MyViewHolder>
{
    Context context;
    ArrayList<ConstTransaksi> items;

    LatLng posLatLng;
    GoogleMap mMap;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBarang;
        TextView namaSampah, jarakSampah;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgBarang = itemView.findViewById(R.id.imgBarang);
            namaSampah = itemView.findViewById(R.id.namaSampah);
            jarakSampah = itemView.findViewById(R.id.jarakSampah);
        }
    }

    @Override
    public AdapterSelectorLoak.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_loakmap_selector,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSelectorLoak.MyViewHolder holder, int position) {
        ConstTransaksi obj = items.get(position);

        holder.namaSampah.setText(obj.getNAMA_BARANG_LOAK());

        Glide.with(context).load(obj.getGambarBarang()).into(holder.imgBarang);

        if(posLatLng.longitude != 0.0 || posLatLng.latitude != 0.0)
        {
            Double jarak = distance(posLatLng.latitude, posLatLng.longitude, Double.valueOf(obj.getLAT_MEMBER()), Double.parseDouble(obj.getLONG_MEMBER()));
            holder.jarakSampah.setText("Jarak : "+String.format("%.2f", jarak)+" Km");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng( Double.parseDouble(obj.getLAT_MEMBER()),
                                Double.parseDouble(obj.getLONG_MEMBER())));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<ConstTransaksi> items) {
        this.items = items;
    }

    public void setPosLatLng(LatLng posLatLng) {
        this.posLatLng = posLatLng;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
