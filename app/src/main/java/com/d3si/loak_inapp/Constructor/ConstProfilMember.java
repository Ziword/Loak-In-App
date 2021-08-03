package com.d3si.loak_inapp.Constructor;

public class ConstProfilMember
{
    private String NAMA, EMAIL, TELP, JK, TGL_DAFTAR, TOTAL_ORDER, ORDER_BERHASIL;

    public ConstProfilMember(String NAMA, String EMAIL, String TELP, String JK, String TGL_DAFTAR, String TOTAL_ORDER, String ORDER_BERHASIL) {
        this.NAMA = NAMA;
        this.EMAIL = EMAIL;
        this.TELP = TELP;
        this.JK = JK;
        this.TGL_DAFTAR = TGL_DAFTAR;
        this.TOTAL_ORDER = TOTAL_ORDER;
        this.ORDER_BERHASIL = ORDER_BERHASIL;
    }

    public String getNAMA() {
        return NAMA;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getTELP() {
        return TELP;
    }

    public String getJK() {
        return JK;
    }

    public String getTGL_DAFTAR() {
        return TGL_DAFTAR;
    }

    public String getTOTAL_ORDER() {
        return TOTAL_ORDER;
    }

    public String getORDER_BERHASIL() {
        return ORDER_BERHASIL;
    }
}
