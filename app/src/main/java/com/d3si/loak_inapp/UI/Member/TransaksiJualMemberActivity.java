package com.d3si.loak_inapp.UI.Member;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.d3si.loak_inapp.Constructor.ConstTransaksi;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.FilePath;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.NotificationCaller;
import com.d3si.loak_inapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransaksiJualMemberActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;

    Button btnJualBarangLoak;

    EditText nama_barang, berat_barang, keterangan_barang, alamat_barang;
    RadioButton rbJenisBarang;
    RadioGroup rgJenisBarang;

    TextView backMenu;

    String latBarang, longBarang;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String jenisBarang;

    MyDB db;

    Uri fileUri;
    String displayName="", filePath;

    ImageView add_img;
    TextView namaGambar;
    int CODE_PICK_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_jual_member);

        nama_barang = findViewById(R.id.jualNamaBarangLoak);
        berat_barang = findViewById(R.id.jualBeratBarangLoak);
        keterangan_barang = findViewById(R.id.jualKeteranganBarangLoak);
        alamat_barang = findViewById(R.id.jualAlamatBarangLoak);
        rgJenisBarang = findViewById(R.id.rgJenisBarangLoak);

        add_img = findViewById(R.id.add_img);
        namaGambar = findViewById(R.id.namaGambar);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(TransaksiJualMemberActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if(multiplePermissionsReport.areAllPermissionsGranted())
                                {
                                    Intent intent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(Intent.createChooser(intent, "select image"),
                                            CODE_PICK_IMAGE);
                                } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied())
                                {
                                    Toast.makeText(TransaksiJualMemberActivity.this," Aplikasi tidak mendapat izin penyimpnan. ",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Transaksi");

        DBConnector();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent intent = getIntent();
        latBarang = intent.getStringExtra("lat");
        longBarang = intent.getStringExtra("long");

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnJualBarangLoak = findViewById(R.id.btnJualBarangLoak);
        btnJualBarangLoak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String namaBarang = nama_barang.getText().toString();
                String beratBarang = berat_barang.getText().toString();
                String keteranganBarang = keterangan_barang.getText().toString();
                String alamatBarang = alamat_barang.getText().toString();
                String idMember = mAuth.getCurrentUser().getUid();


                if (rgJenisBarang.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(TransaksiJualMemberActivity.this, "Kategori barang loak harus dipilih ", Toast.LENGTH_LONG).show();
                    rgJenisBarang.requestFocus();
                    return;
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = rgJenisBarang.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    rbJenisBarang = (RadioButton)findViewById(selectedId);
                }

                jenisBarang = rbJenisBarang.getText().toString();

                if(jenisBarang.isEmpty()){
                    Toast.makeText(TransaksiJualMemberActivity.this, "Jenis Barang belum dipilih", Toast.LENGTH_LONG).show();
                    rgJenisBarang.requestFocus();
                    return;
                }

                if(namaBarang.isEmpty()){
                    nama_barang.setError("Nama barang tidak boleh kosong");
                    nama_barang.requestFocus();
                    return;
                }

                if(beratBarang.isEmpty()){
                    berat_barang.setError("Nama barang tidak boleh kosong");
                    berat_barang.requestFocus();
                    return;
                }

                if(keteranganBarang.isEmpty()){
                    keterangan_barang.setError("Nama barang tidak boleh kosong");
                    keterangan_barang.requestFocus();
                    return;
                }

                if(alamatBarang.isEmpty()){
                    alamat_barang.setError("Nama barang tidak boleh kosong");
                    alamat_barang.requestFocus();
                    return;
                }

                if(displayName.isEmpty())
                {
                    Toast.makeText(TransaksiJualMemberActivity.this, "Gambar harus dipilih.",Toast.LENGTH_LONG).show();
                    add_img.requestFocus();
                    return;
                }

                uploadData(namaBarang, beratBarang, keteranganBarang, alamatBarang, idMember, jenisBarang);
            }
        });
    }

    private void uploadData(String namaBarang, String beratBarang, String keteranganBarang, String alamatBarang, String idMember, String jenisBarang)
    {
        //Get Unique ID
        DatabaseReference pushedPostRef = databaseReference.push();
        String postId = pushedPostRef.getKey();
        ConstTransaksi constTransaksi = new ConstTransaksi(postId,"JT1","ST1",idMember,"",namaBarang,jenisBarang,beratBarang,getDate(),keteranganBarang, alamatBarang, latBarang.toString(), longBarang.toString(), displayName);
        databaseReference.child(postId).setValue(constTransaksi);
        NotificationCaller notificationCaller = new NotificationCaller(TransaksiJualMemberActivity.this);
        notificationCaller.notifUploadLoak();

//        Get File Path
        try {
            filePath = FilePath.getPath(TransaksiJualMemberActivity.this, fileUri);
        } catch (Exception e){
            Toast.makeText(TransaksiJualMemberActivity.this, "Penyimpanan tidak dapat diakses.",Toast.LENGTH_LONG).show();
        }

//        Multipart Request
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), new File(filePath));
        MultipartBody.Part gambar_barang = MultipartBody.Part.createFormData("gambar_barang", new File(filePath).getName(), requestBody);
        RequestBody id_transaksi = RequestBody.create(MediaType.parse("text/plain"), postId);
        RequestBody id_member = RequestBody.create(MediaType.parse("text/plain"), idMember);
        RequestBody lat_barang = RequestBody.create(MediaType.parse("text/plain"), latBarang);
        RequestBody long_barang = RequestBody.create(MediaType.parse("text/plain"), longBarang);
        RequestBody nama_barang = RequestBody.create(MediaType.parse("text/plain"), namaBarang);
        RequestBody jenis_barang = RequestBody.create(MediaType.parse("text/plain"), jenisBarang);
        RequestBody berat_barang = RequestBody.create(MediaType.parse("text/plain"), beratBarang);
        RequestBody keterangan_barang = RequestBody.create(MediaType.parse("text/plain"), keteranganBarang);
        RequestBody alamat_barang = RequestBody.create(MediaType.parse("text/plain"), alamatBarang);

        Call<ResponseBody> call = db.tambahLoak(id_transaksi, id_member, lat_barang, long_barang, nama_barang, jenis_barang, berat_barang, keterangan_barang, alamat_barang, gambar_barang);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Intent i = new Intent(TransaksiJualMemberActivity.this, HomeActivityMember.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                TransaksiJualMemberActivity.this.finish();
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    fileUri = data.getData();
                    displayName = FilePath.getNameFromContentUri(TransaksiJualMemberActivity.this, fileUri);
                    try {
                        Glide.with(this).load(new File(FilePath.getPath(TransaksiJualMemberActivity.this, fileUri))).into(add_img);
                        namaGambar.setText(displayName);
                        namaGambar.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        System.out.println("RESPONSE : " + e.getMessage());
                    }
                }
                break;
        }
    }

    private String getDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private void DBConnector()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}