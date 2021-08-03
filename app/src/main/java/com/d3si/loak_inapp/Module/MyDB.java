package com.d3si.loak_inapp.Module;

import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiAgen;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Constructor.ConstAgen;
import com.d3si.loak_inapp.Constructor.ConstChart;
import com.d3si.loak_inapp.Constructor.ConstLogic;
import com.d3si.loak_inapp.Constructor.ConstProfilAgen;
import com.d3si.loak_inapp.Constructor.ConstProfilMember;
import com.d3si.loak_inapp.Constructor.ConstTransaksi;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MyDB
{
    @POST("/LoakIn/ControllerAndroid/getAgen")
    @FormUrlEncoded
    Call<ConstAgen> cekDaftarAgen(@Field("emailAgen") String emailAgen);

    @POST("/LoakIn/ControllerAndroid/daftarMember")
    @FormUrlEncoded
    Call<ResponseBody> daftarMember(@Field("idMember") String idMember, @Field("idJenisUser") String jenisUser, @Field("nama") String nama, @Field("email") String email, @Field("jenkel") String jenkel, @Field("telp") String telp);

    @POST("/LoakIn/ControllerAndroid/checkUser")
    @FormUrlEncoded
    Call<ConstLogic> checkUser(@Field("email") String email);

    @POST("/LoakIn/ControllerAndroid/updateAgenUserID")
    @FormUrlEncoded
    Call<ResponseBody> updateAgenUserID(@Field("emailAgen") String email, @Field("userID") String UID);

    @POST("/LoakIn/ControllerAndroid/tambahBarangLoak")
    @Multipart
    Call<ResponseBody> tambahLoak(@Part("id_transaksi") RequestBody id_transaksi,
                                  @Part("id_member") RequestBody id_member,
                                  @Part("lat_barang") RequestBody lat_barang,
                                  @Part("long_barang") RequestBody long_barang,
                                  @Part("nama_barang") RequestBody nama_barang,
                                  @Part("jenis_barang") RequestBody jenis_barang,
                                  @Part("berat_barang") RequestBody berat_barang,
                                  @Part("keterangan_barang") RequestBody keterangan_barang,
                                  @Part("alamat_barang") RequestBody alamat_barang,
                                  @Part MultipartBody.Part gambar_barang);

    @POST("/LoakIn/ControllerAndroid/bacaProsesTransaksiMember")
    @FormUrlEncoded
    Call<ArrayList<ConstAdapterTransaksiMember>> bacaProsesTransaksiMember(@Field("id_member") String id_member);

    @POST("/LoakIn/ControllerAndroid/bacaSelesaiTransaksiMember")
    @FormUrlEncoded
    Call<ArrayList<ConstAdapterTransaksiMember>> bacaSelesaiTransaksiMember(@Field("id_member") String id_member);

    @GET("/LoakIn/ControllerAndroid/getDataAgen")
    Call<ArrayList<ConstAgen>> getListAgen();

    @GET("/LoakIn/ControllerAndroid/getTransaksiJual")
    Call<ArrayList<ConstTransaksi>> getTransaksiJual();

    @POST("/LoakIn/ControllerAndroid/getDetailTransaksi")
    @FormUrlEncoded
    Call<ConstTransaksi> getDetailTransaksi(@Field("id_transaksi") String id_transaksi);

    @POST("/LoakIn/ControllerAndroid/updateTransaksi")
    @FormUrlEncoded
    Call <ResponseBody> updateTransaksi(@Field("id_transaksi") String id_transaksi, @Field("id_agen") String id_agen, @Field("status_transaksi") String status_transaksi);

    @POST("/LoakIn/ControllerAndroid/cancelTransaksi")
    @FormUrlEncoded
    Call <ResponseBody> cancelTransaksi(@Field("id_transaksi") String id_transaksi, @Field("status_transaksi") String status_transaksi);

    @POST("/LoakIn/ControllerAndroid/readTransaksiAgen")
    @FormUrlEncoded
    Call<ArrayList<ConstAdapterTransaksiAgen>> reaedTransaksiAgen(@Field("uid_agen") String uid_agen);

    @POST("/LoakIn/ControllerAndroid/readTransaksiSelesaiAgen")
    @FormUrlEncoded
    Call<ArrayList<ConstAdapterTransaksiAgen>> readTransaksiSelesaiAgen(@Field("uid_agen") String uid_agen);

    @POST("/LoakIn/ControllerAndroid/getProfilMember")
    @FormUrlEncoded
    Call<ConstProfilMember> getProfilMember(@Field("id_member") String id_member);

    @POST("/LoakIn/ControllerAndroid/getProfilAgen")
    @FormUrlEncoded
    Call<ConstProfilAgen> getProfilAgen(@Field("uid_agen") String uid_agen);

    @POST("/LoakIn/ControllerAndroid/getChartData")
    @FormUrlEncoded
    Call<ArrayList<ConstChart>> getChart(@Field("JU") String JU, @Field("ID")String ID);
}
