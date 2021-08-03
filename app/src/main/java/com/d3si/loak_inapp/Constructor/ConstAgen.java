package com.d3si.loak_inapp.Constructor;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstAgen
{
    @SerializedName("ID_AGEN")
    @Expose
    private String ID_AGEN;
    @SerializedName("ID_JENIS_USER")
    @Expose
    String ID_JENIS_USER;
    @SerializedName("NAMA_AGEN")
    @Expose
    String NAMA_AGEN;
    @SerializedName("EMAIL_AGEN")
    @Expose
    String EMAIL_AGEN;
    @SerializedName("AGEN_USER_ID")
    @Expose
    String AGEN_USER_ID;
    @SerializedName("NO_TELP_AGEN")
    @Expose
    String NO_TELP_AGEN;
    @SerializedName("ALAMAT_LENGKAP_AGEN")
    @Expose
    String ALAMAT_LENGKAP_AGEN;
    @SerializedName("LONGTITUDE")
    @Expose
    String LONGTITUDE;
    @SerializedName("LATITUDE")
    @Expose
    String LATITUDE;
    @SerializedName("TANGGAL_DAFTAR_AGEN")
    @Expose
    String TANGGAL_DAFTAR_AGEN;
    @SerializedName("MESSAGE")
    @Expose
    String MESSAGE="1";

    public ConstAgen(String ID_AGEN, String ID_JENIS_USER, String NAMA_AGEN, String EMAIL_AGEN, String AGEN_USER_ID, String NO_TELP_AGEN, String ALAMAT_LENGKAP_AGEN, String LONGTITUDE, String LATITUDE, String TANGGAL_DAFTAR_AGEN) {
        this.ID_AGEN = ID_AGEN;
        this.ID_JENIS_USER = ID_JENIS_USER;
        this.NAMA_AGEN = NAMA_AGEN;
        this.EMAIL_AGEN = EMAIL_AGEN;
        this.AGEN_USER_ID = AGEN_USER_ID;
        this.NO_TELP_AGEN = NO_TELP_AGEN;
        this.ALAMAT_LENGKAP_AGEN = ALAMAT_LENGKAP_AGEN;
        this.LONGTITUDE = LONGTITUDE;
        this.LATITUDE = LATITUDE;
        this.TANGGAL_DAFTAR_AGEN = TANGGAL_DAFTAR_AGEN;
    }

    public ConstAgen(){}

    @PropertyName("ID_AGEN")
    public String getID_AGEN() {
        return ID_AGEN;
    }

    @PropertyName("ID_AGEN")
    public void setID_AGEN(String ID_AGEN) {
        this.ID_AGEN = ID_AGEN;
    }

    @PropertyName("ID_JENIS_USER")
    public String getID_JENIS_USER() {
        return ID_JENIS_USER;
    }

    @PropertyName("ID_JENIS_USER")
    public void setID_JENIS_USER(String ID_JENIS_USER) {
        this.ID_JENIS_USER = ID_JENIS_USER;
    }

    @PropertyName("NAMA_AGEN")
    public String getNAMA_AGEN() {
        return NAMA_AGEN;
    }

    @PropertyName("NAMA_AGEN")
    public void setNAMA_AGEN(String NAMA_AGEN) {
        this.NAMA_AGEN = NAMA_AGEN;
    }

    @PropertyName("EMAIL_AGEN")
    public String getEMAIL_AGEN() {
        return EMAIL_AGEN;
    }

    @PropertyName("EMAIL_AGEN")
    public void setEMAIL_AGEN(String EMAIL_AGEN) {
        this.EMAIL_AGEN = EMAIL_AGEN;
    }

    @PropertyName("AGEN_USER_ID")
    public String getAGEN_USER_ID() {
        return AGEN_USER_ID;
    }

    @PropertyName("AGEN_USER_ID")
    public void setAGEN_USER_ID(String AGEN_USER_ID) {
        this.AGEN_USER_ID = AGEN_USER_ID;
    }

    @PropertyName("NO_TELP_AGEN")
    public String getNO_TELP_AGEN() {
        return NO_TELP_AGEN;
    }

    @PropertyName("NO_TELP_AGEN")
    public void setNO_TELP_AGEN(String NO_TELP_AGEN) {
        this.NO_TELP_AGEN = NO_TELP_AGEN;
    }

    @PropertyName("ALAMAT_LENGKAP_AGEN")
    public String getALAMAT_LENGKAP_AGEN() {
        return ALAMAT_LENGKAP_AGEN;
    }

    @PropertyName("ALAMAT_LENGKAP_AGEN")
    public void setALAMAT_LENGKAP_AGEN(String ALAMAT_LENGKAP_AGEN) {
        this.ALAMAT_LENGKAP_AGEN = ALAMAT_LENGKAP_AGEN;
    }

    @PropertyName("LONGTITUDE")
    public String getLONGTITUDE() {
        return LONGTITUDE;
    }

    @PropertyName("LONGTITUDE")
    public void setLONGTITUDE(String LONGTITUDE) {
        this.LONGTITUDE = LONGTITUDE;
    }

    @PropertyName("LATITUDE")
    public String getLATITUDE() {
        return LATITUDE;
    }

    @PropertyName("LATITUDE")
    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    @PropertyName("TANGGAL_DAFTAR_AGEN")
    public String getTANGGAL_DAFTAR_AGEN() {
        return TANGGAL_DAFTAR_AGEN;
    }

    @PropertyName("TANGGAL_DAFTAR_AGEN")
    public void setTANGGAL_DAFTAR_AGEN(String TANGGAL_DAFTAR_AGEN) {
        this.TANGGAL_DAFTAR_AGEN = TANGGAL_DAFTAR_AGEN;
    }

    @PropertyName("MESSAGE")
    public String getMESSAGE() {
        return MESSAGE;
    }

    @PropertyName("MESSAGE")
    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
