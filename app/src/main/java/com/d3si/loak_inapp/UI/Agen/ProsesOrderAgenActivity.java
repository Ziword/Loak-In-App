package com.d3si.loak_inapp.UI.Agen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d3si.loak_inapp.Adapter.Agen.AdapterProsesTransaksiAgen;
import com.d3si.loak_inapp.Adapter.Member.AdapterProsesTransaksiMember;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiAgen;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProsesOrderAgenActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<ConstAdapterTransaksiAgen> data = new ArrayList<>();
    private AdapterProsesTransaksiAgen adapterProsesTransaksiAgen;

    TextView backMenu;

    MyDB db;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_order_agen);

        DBBuilder();
        mAuth = FirebaseAuth.getInstance();

        rv = findViewById(R.id.rv_ProsesTransaksiAgen);
        RecyclerView.LayoutManager layRv = new LinearLayoutManager(ProsesOrderAgenActivity.this);
        rv.setLayoutManager(layRv);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapterProsesTransaksiAgen = new AdapterProsesTransaksiAgen(data, ProsesOrderAgenActivity.this);
        rv.setAdapter(adapterProsesTransaksiAgen);

        getDataProsesTransaksiMember();

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataProsesTransaksiMember()
    {
        Call<ArrayList<ConstAdapterTransaksiAgen>> call = db.reaedTransaksiAgen(mAuth.getCurrentUser().getUid());
        call.enqueue(new Callback<ArrayList<ConstAdapterTransaksiAgen>>() {
            @Override
            public void onResponse(Call<ArrayList<ConstAdapterTransaksiAgen>> call, Response<ArrayList<ConstAdapterTransaksiAgen>> response) {
                data = response.body();
                if(response.body().size()>0)
                {
                    adapterProsesTransaksiAgen.setContext(ProsesOrderAgenActivity.this);
                    adapterProsesTransaksiAgen.setItems(data);
                    adapterProsesTransaksiAgen.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConstAdapterTransaksiAgen>> call, Throwable t) {

            }
        });
    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}