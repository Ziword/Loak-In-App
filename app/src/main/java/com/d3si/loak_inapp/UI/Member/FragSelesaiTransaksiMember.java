package com.d3si.loak_inapp.UI.Member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d3si.loak_inapp.Adapter.Member.AdapterSelesaiTransaksiMember;
import com.d3si.loak_inapp.Constructor.ConstAdapterTransaksiMember;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragSelesaiTransaksiMember#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragSelesaiTransaksiMember extends Fragment {
    MyDB db;

    private RecyclerView rv;
    private ArrayList<ConstAdapterTransaksiMember> data = new ArrayList<>();
    private AdapterSelesaiTransaksiMember adapterSelesaiTransaksiMember;

    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragSelesaiTransaksiMember() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragSelesaiTransaksiMember.
     */
    // TODO: Rename and change types and number of parameters
    public static FragSelesaiTransaksiMember newInstance(String param1, String param2) {
        FragSelesaiTransaksiMember fragment = new FragSelesaiTransaksiMember();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_selesai_transaksi_member, container, false);
        // Inflate the layout for this fragment

        DBBuilder();
        mAuth = FirebaseAuth.getInstance();

        rv = view.findViewById(R.id.rv_SelesaiTransaksiMember);
        RecyclerView.LayoutManager layRv = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(layRv);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapterSelesaiTransaksiMember = new AdapterSelesaiTransaksiMember(data, view.getContext());
        rv.setAdapter(adapterSelesaiTransaksiMember);

        getDataSelesaiTransaksiMember(view);

        return view;
    }

    private void getDataSelesaiTransaksiMember(View view)
    {
        Call<ArrayList<ConstAdapterTransaksiMember>> call = db.bacaSelesaiTransaksiMember(mAuth.getCurrentUser().getUid());
        call.enqueue(new Callback<ArrayList<ConstAdapterTransaksiMember>>() {
            @Override
            public void onResponse(Call<ArrayList<ConstAdapterTransaksiMember>> call, Response<ArrayList<ConstAdapterTransaksiMember>> response) {
                data = response.body();
                if(response.body().size()>0)
                {
                    adapterSelesaiTransaksiMember.setContext(view.getContext());
                    adapterSelesaiTransaksiMember.setItems(data);
                    adapterSelesaiTransaksiMember.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConstAdapterTransaksiMember>> call, Throwable t) {

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