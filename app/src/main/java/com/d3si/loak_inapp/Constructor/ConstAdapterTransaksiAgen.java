package com.d3si.loak_inapp.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

import com.d3si.loak_inapp.Module.DB_BaseURL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ConstAdapterTransaksiAgen implements Parcelable {
    private String ID_TRANSAKSI, ID_JENIS_TRANSAKSI, ID_STATUS_TRANSAKSI, ID_MEMBER, ID_AGEN, NAMA_BARANG_LOAK, JENIS_BARANG, BERAT_BARANG, TANGGAL_TRANSAKSI, KETERANGAN_TRANSAKSI, ALAMAT_TRANSAKSI, LAT_MEMBER, LONG_MEMBER, NAMA_LENGKAP_MEMBER, NO_TELP_MEMBER, NAMA_STATUS_TRANSAKSI, FOTO_BARANG;
    Calendar calendar;

    public ConstAdapterTransaksiAgen()
    {

    }

    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    public String getID_JENIS_TRANSAKSI() {
        return ID_JENIS_TRANSAKSI;
    }

    public String getID_STATUS_TRANSAKSI() {
        return ID_STATUS_TRANSAKSI;
    }

    public String getID_MEMBER() {
        return ID_MEMBER;
    }

    public String getID_AGEN() {
        return ID_AGEN;
    }

    public String getNAMA_BARANG_LOAK() {
        return NAMA_BARANG_LOAK;
    }

    public String getJENIS_BARANG() {
        return JENIS_BARANG;
    }

    public String getBERAT_BARANG() {
        return BERAT_BARANG;
    }

    public String getTANGGAL_TRANSAKSI() {
        return TANGGAL_TRANSAKSI;
    }

    public String getKETERANGAN_TRANSAKSI() {
        return KETERANGAN_TRANSAKSI;
    }

    public String getALAMAT_TRANSAKSI() {
        return ALAMAT_TRANSAKSI;
    }

    public String getLAT_MEMBER() {
        return LAT_MEMBER;
    }

    public String getLONG_MEMBER() {
        return LONG_MEMBER;
    }

    public String getNAMA_LENGKAP_MEMBER() {
        return NAMA_LENGKAP_MEMBER;
    }

    public String getNO_TELP_MEMBER() {
        return NO_TELP_MEMBER;
    }

    public String getNAMA_STATUS_TRANSAKSI() {
        return NAMA_STATUS_TRANSAKSI;
    }

    public String getFOTO_BARANG() {
        return FOTO_BARANG;
    }

    public String getTanggal() throws ParseException {
        dateFormatter();
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public String getBulan() throws ParseException {
        dateFormatter();
        String bulan = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        return bulan;
    }

    public String getTahun() throws ParseException {
        dateFormatter();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public void dateFormatter() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getTANGGAL_TRANSAKSI());
        calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
    }

    public String getGambarBarang() {
        DB_BaseURL db = new DB_BaseURL();
        return db.getUrl() +"storage/uploads/LoakIn/FotoBarang/"+getFOTO_BARANG();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID_TRANSAKSI);
        dest.writeString(this.ID_JENIS_TRANSAKSI);
        dest.writeString(this.ID_STATUS_TRANSAKSI);
        dest.writeString(this.ID_MEMBER);
        dest.writeString(this.ID_AGEN);
        dest.writeString(this.NAMA_BARANG_LOAK);
        dest.writeString(this.JENIS_BARANG);
        dest.writeString(this.BERAT_BARANG);
        dest.writeString(this.TANGGAL_TRANSAKSI);
        dest.writeString(this.KETERANGAN_TRANSAKSI);
        dest.writeString(this.ALAMAT_TRANSAKSI);
        dest.writeString(this.LAT_MEMBER);
        dest.writeString(this.LONG_MEMBER);
        dest.writeString(this.NAMA_LENGKAP_MEMBER);
        dest.writeString(this.NO_TELP_MEMBER);
        dest.writeString(this.NAMA_STATUS_TRANSAKSI);
        dest.writeString(this.FOTO_BARANG);
        dest.writeSerializable(this.calendar);
    }

    public void readFromParcel(Parcel source) {
        this.ID_TRANSAKSI = source.readString();
        this.ID_JENIS_TRANSAKSI = source.readString();
        this.ID_STATUS_TRANSAKSI = source.readString();
        this.ID_MEMBER = source.readString();
        this.ID_AGEN = source.readString();
        this.NAMA_BARANG_LOAK = source.readString();
        this.JENIS_BARANG = source.readString();
        this.BERAT_BARANG = source.readString();
        this.TANGGAL_TRANSAKSI = source.readString();
        this.KETERANGAN_TRANSAKSI = source.readString();
        this.ALAMAT_TRANSAKSI = source.readString();
        this.LAT_MEMBER = source.readString();
        this.LONG_MEMBER = source.readString();
        this.NAMA_LENGKAP_MEMBER = source.readString();
        this.NO_TELP_MEMBER = source.readString();
        this.NAMA_STATUS_TRANSAKSI = source.readString();
        this.FOTO_BARANG = source.readString();
        this.calendar = (Calendar) source.readSerializable();
    }

    protected ConstAdapterTransaksiAgen(Parcel in) {
        this.ID_TRANSAKSI = in.readString();
        this.ID_JENIS_TRANSAKSI = in.readString();
        this.ID_STATUS_TRANSAKSI = in.readString();
        this.ID_MEMBER = in.readString();
        this.ID_AGEN = in.readString();
        this.NAMA_BARANG_LOAK = in.readString();
        this.JENIS_BARANG = in.readString();
        this.BERAT_BARANG = in.readString();
        this.TANGGAL_TRANSAKSI = in.readString();
        this.KETERANGAN_TRANSAKSI = in.readString();
        this.ALAMAT_TRANSAKSI = in.readString();
        this.LAT_MEMBER = in.readString();
        this.LONG_MEMBER = in.readString();
        this.NAMA_LENGKAP_MEMBER = in.readString();
        this.NO_TELP_MEMBER = in.readString();
        this.NAMA_STATUS_TRANSAKSI = in.readString();
        this.FOTO_BARANG = in.readString();
        this.calendar = (Calendar) in.readSerializable();
    }

    public static final Creator<ConstAdapterTransaksiAgen> CREATOR = new Creator<ConstAdapterTransaksiAgen>() {
        @Override
        public ConstAdapterTransaksiAgen createFromParcel(Parcel source) {
            return new ConstAdapterTransaksiAgen(source);
        }

        @Override
        public ConstAdapterTransaksiAgen[] newArray(int size) {
            return new ConstAdapterTransaksiAgen[size];
        }
    };
}
