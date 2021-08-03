package com.d3si.loak_inapp.Constructor;

import android.os.Parcel;
import android.os.Parcelable;

import com.d3si.loak_inapp.Module.DB_BaseURL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ConstAdapterTransaksiMember implements Parcelable {
    String ID_TRANSAKSI, NAMA_BARANG_LOAK, NAMA_STATUS_TRANSAKSI, TANGGAL_TRANSAKSI, JENIS_BARANG, KETERANGAN_TRANSAKSI, ALAMAT_TRANSAKSI, FOTO_BARANG;
    int BERAT_BARANG;

    Calendar calendar;

    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    public String getNAMA_BARANG_LOAK() {
        return NAMA_BARANG_LOAK;
    }

    public String getNAMA_STATUS_TRANSAKSI() {
        return NAMA_STATUS_TRANSAKSI;
    }

    public String getTANGGAL_TRANSAKSI() {
        return TANGGAL_TRANSAKSI;
    }

    public String getJENIS_BARANG() {
        return JENIS_BARANG;
    }

    public String getKETERANGAN_TRANSAKSI() {
        return KETERANGAN_TRANSAKSI;
    }

    public String getALAMAT_TRANSAKSI() {
        return ALAMAT_TRANSAKSI;
    }

    public int getBERAT_BARANG() {
        return BERAT_BARANG;
    }

    public String getFOTO_BARANG() {
        return FOTO_BARANG;
    }

    public String getGambarBarang() {
        DB_BaseURL db = new DB_BaseURL();
        return db.getUrl() +"storage/uploads/LoakIn/FotoBarang/"+getFOTO_BARANG();
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID_TRANSAKSI);
        dest.writeString(this.NAMA_BARANG_LOAK);
        dest.writeString(this.NAMA_STATUS_TRANSAKSI);
        dest.writeString(this.TANGGAL_TRANSAKSI);
        dest.writeString(this.JENIS_BARANG);
        dest.writeString(this.KETERANGAN_TRANSAKSI);
        dest.writeString(this.ALAMAT_TRANSAKSI);
        dest.writeString(this.FOTO_BARANG);
        dest.writeInt(this.BERAT_BARANG);
        dest.writeSerializable(this.calendar);
    }

    public void readFromParcel(Parcel source) {
        this.ID_TRANSAKSI = source.readString();
        this.NAMA_BARANG_LOAK = source.readString();
        this.NAMA_STATUS_TRANSAKSI = source.readString();
        this.TANGGAL_TRANSAKSI = source.readString();
        this.JENIS_BARANG = source.readString();
        this.KETERANGAN_TRANSAKSI = source.readString();
        this.ALAMAT_TRANSAKSI = source.readString();
        this.FOTO_BARANG = source.readString();
        this.BERAT_BARANG = source.readInt();
        this.calendar = (Calendar) source.readSerializable();
    }

    public ConstAdapterTransaksiMember() {
    }

    protected ConstAdapterTransaksiMember(Parcel in) {
        this.ID_TRANSAKSI = in.readString();
        this.NAMA_BARANG_LOAK = in.readString();
        this.NAMA_STATUS_TRANSAKSI = in.readString();
        this.TANGGAL_TRANSAKSI = in.readString();
        this.JENIS_BARANG = in.readString();
        this.KETERANGAN_TRANSAKSI = in.readString();
        this.ALAMAT_TRANSAKSI = in.readString();
        this.FOTO_BARANG = in.readString();
        this.BERAT_BARANG = in.readInt();
        this.calendar = (Calendar) in.readSerializable();
    }

    public static final Creator<ConstAdapterTransaksiMember> CREATOR = new Creator<ConstAdapterTransaksiMember>() {
        @Override
        public ConstAdapterTransaksiMember createFromParcel(Parcel source) {
            return new ConstAdapterTransaksiMember(source);
        }

        @Override
        public ConstAdapterTransaksiMember[] newArray(int size) {
            return new ConstAdapterTransaksiMember[size];
        }
    };
}
