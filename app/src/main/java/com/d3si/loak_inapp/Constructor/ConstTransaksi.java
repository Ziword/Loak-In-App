package com.d3si.loak_inapp.Constructor;

import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstTransaksi
{
    @SerializedName("ID_TRANSAKSI")
    @Expose
    String ID_TRANSAKSI;

    @SerializedName("ID_JENIS_TRANSAKSI")
    @Expose
    String ID_JENIS_TRANSAKSI;

    @SerializedName("ID_STATUS_TRANSAKSI")
    @Expose
    String ID_STATUS_TRANSAKSI;

    @SerializedName("ID_MEMBER")
    @Expose
    String ID_MEMBER;

    @SerializedName("ID_AGEN")
    @Expose
    String ID_AGEN;

    @SerializedName("NAMA_BARANG_LOAK")
    @Expose
    String NAMA_BARANG_LOAK;

    @SerializedName("JENIS_BARANG")
    @Expose
    String JENIS_BARANG;

    @SerializedName("BERAT_BARANG")
    @Expose
    String BERAT_BARANG;

    @SerializedName("TANGGAL_TRANSAKSI")
    @Expose
    String TANGGAL_TRANSAKSI;

    @SerializedName("KETERANGAN_TRANSAKSI")
    @Expose
    String KETERANGAN_TRANSAKSI;

    @SerializedName("ALAMAT_TRANSAKSI")
    @Expose
    String ALAMAT_TRANSAKSI;

    @SerializedName("LAT_MEMBER")
    @Expose
    String LAT_MEMBER;

    @SerializedName("LONG_MEMBER")
    @Expose
    String LONG_MEMBER;

    @SerializedName("NAMA_LENGKAP_MEMBER")
    @Expose
    String NAMA_LENGKAP_MEMBER;

    @SerializedName("NO_TELP_MEMBER")
    @Expose
    String NO_TELP_MEMBER;

    @SerializedName("FOTO_BARANG")
    @Expose
    String FOTO_BARANG;

    public ConstTransaksi(String ID_TRANSAKSI, String ID_JENIS_TRANSAKSI, String ID_STATUS_TRANSAKSI, String ID_MEMBER, String ID_AGEN, String NAMA_BARANG_LOAK, String JENIS_BARANG, String BERAT_BARANG, String TANGGAL_TRANSAKSI, String KETERANGAN_TRANSAKSI, String ALAMAT_TRANSAKSI, String LAT_MEMBER, String LONG_MEMBER, String FOTO_BARANG) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
        this.ID_JENIS_TRANSAKSI = ID_JENIS_TRANSAKSI;
        this.ID_STATUS_TRANSAKSI = ID_STATUS_TRANSAKSI;
        this.ID_MEMBER = ID_MEMBER;
        this.ID_AGEN = ID_AGEN;
        this.NAMA_BARANG_LOAK = NAMA_BARANG_LOAK;
        this.JENIS_BARANG = JENIS_BARANG;
        this.BERAT_BARANG = BERAT_BARANG;
        this.TANGGAL_TRANSAKSI = TANGGAL_TRANSAKSI;
        this.KETERANGAN_TRANSAKSI = KETERANGAN_TRANSAKSI;
        this.ALAMAT_TRANSAKSI = ALAMAT_TRANSAKSI;
        this.LAT_MEMBER = LAT_MEMBER;
        this.LONG_MEMBER = LONG_MEMBER;
        this.FOTO_BARANG = FOTO_BARANG;
    }

    public ConstTransaksi() {
    }

    public String getGambarBarang() {
        DB_BaseURL db = new DB_BaseURL();
        return db.getUrl() +"storage/uploads/LoakIn/FotoBarang/"+getFOTO_BARANG();
    }

    @PropertyName("ID_TRANSAKSI")
    public String getID_TRANSAKSI() {
        return ID_TRANSAKSI;
    }

    @PropertyName("ID_TRANSAKSI")
    public void setID_TRANSAKSI(String ID_TRANSAKSI) {
        this.ID_TRANSAKSI = ID_TRANSAKSI;
    }

    @PropertyName("ID_JENIS_TRANSAKSI")
    public String getID_JENIS_TRANSAKSI() {
        return ID_JENIS_TRANSAKSI;
    }

    @PropertyName("ID_JENIS_TRANSAKSI")
    public void setID_JENIS_TRANSAKSI(String ID_JENIS_TRANSAKSI) {
        this.ID_JENIS_TRANSAKSI = ID_JENIS_TRANSAKSI;
    }

    @PropertyName("ID_STATUS_TRANSAKSI")
    public String getID_STATUS_TRANSAKSI() {
        return ID_STATUS_TRANSAKSI;
    }

    @PropertyName("ID_STATUS_TRANSAKSI")
    public void setID_STATUS_TRANSAKSI(String ID_STATUS_TRANSAKSI) {
        this.ID_STATUS_TRANSAKSI = ID_STATUS_TRANSAKSI;
    }

    @PropertyName("ID_MEMBER")
    public String getID_MEMBER() {
        return ID_MEMBER;
    }

    @PropertyName("ID_MEMBER")
    public void setID_MEMBER(String ID_MEMBER) {
        this.ID_MEMBER = ID_MEMBER;
    }

    @PropertyName("ID_AGEN")
    public String getID_AGEN() {
        return ID_AGEN;
    }

    @PropertyName("ID_AGEN")
    public void setID_AGEN(String ID_AGEN) {
        this.ID_AGEN = ID_AGEN;
    }

    @PropertyName("NAMA_BARANG_LOAK")
    public String getNAMA_BARANG_LOAK() {
        return NAMA_BARANG_LOAK;
    }

    @PropertyName("NAMA_BARANG_LOAK")
    public void setNAMA_BARANG_LOAK(String NAMA_BARANG_LOAK) {
        this.NAMA_BARANG_LOAK = NAMA_BARANG_LOAK;
    }

    @PropertyName("JENIS_BARANG")
    public String getJENIS_BARANG() {
        return JENIS_BARANG;
    }

    @PropertyName("JENIS_BARANG")
    public void setJENIS_BARANG(String JENIS_BARANG) {
        this.JENIS_BARANG = JENIS_BARANG;
    }

    @PropertyName("TANGGAL_TRANSAKSI")
    public String getTANGGAL_TRANSAKSI() {
        return TANGGAL_TRANSAKSI;
    }

    @PropertyName("TANGGAL_TRANSAKSI")
    public void setTANGGAL_TRANSAKSI(String TANGGAL_TRANSAKSI) {
        this.TANGGAL_TRANSAKSI = TANGGAL_TRANSAKSI;
    }

    @PropertyName("KETERANGAN_TRANSAKSI")
    public String getKETERANGAN_TRANSAKSI() {
        return KETERANGAN_TRANSAKSI;
    }

    @PropertyName("KETERANGAN_TRANSAKSI")
    public void setKETERANGAN_TRANSAKSI(String KETERANGAN_TRANSAKSI) {
        this.KETERANGAN_TRANSAKSI = KETERANGAN_TRANSAKSI;
    }

    @PropertyName("ALAMAT_TRANSAKSI")
    public String getALAMAT_TRANSAKSI() {
        return ALAMAT_TRANSAKSI;
    }

    @PropertyName("ALAMAT_TRANSAKSI")
    public void setALAMAT_TRANSAKSI(String ALAMAT_TRANSAKSI) {
        this.ALAMAT_TRANSAKSI = ALAMAT_TRANSAKSI;
    }

    @PropertyName("LAT_MEMBER")
    public String getLAT_MEMBER() {
        return LAT_MEMBER;
    }

    @PropertyName("LAT_MEMBER")
    public void setLAT_MEMBER(String LAT_MEMBER) {
        this.LAT_MEMBER = LAT_MEMBER;
    }

    @PropertyName("LONG_MEMBER")
    public String getLONG_MEMBER() {
        return LONG_MEMBER;
    }

    @PropertyName("LONG_MEMBER")
    public void setLONG_MEMBER(String LONG_MEMBER) {
        this.LONG_MEMBER = LONG_MEMBER;
    }

    @PropertyName("BERAT_BARANG")
    public String getBERAT_BARANG() {
        return BERAT_BARANG;
    }

    @PropertyName("BERAT_BARANG")
    public void setBERAT_BARANG(String BERAT_BARANG) {
        this.BERAT_BARANG = BERAT_BARANG;
    }

    public String getNAMA_LENGKAP_MEMBER() {
        return NAMA_LENGKAP_MEMBER;
    }

    public String getNO_TELP_MEMBER() {
        return NO_TELP_MEMBER;
    }

    @PropertyName("FOTO_BARANG")
    public String getFOTO_BARANG() {
        return FOTO_BARANG;
    }

    @PropertyName("FOTO_BARANG")
    public void setFOTO_BARANG(String FOTO_BARANG) {
        this.FOTO_BARANG = FOTO_BARANG;
    }

}
