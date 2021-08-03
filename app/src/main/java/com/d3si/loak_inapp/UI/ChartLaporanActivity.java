package com.d3si.loak_inapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.d3si.loak_inapp.Constructor.ConstChart;
import com.d3si.loak_inapp.Module.DB_BaseURL;
import com.d3si.loak_inapp.Module.MyDB;
import com.d3si.loak_inapp.Module.SessionManager;
import com.d3si.loak_inapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChartLaporanActivity extends AppCompatActivity {
    BarChart barChart;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> labelBar;

    TextView backMenu;

    SessionManager session;
    HashMap<String, String> user;

    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_laporan);

        backMenu = findViewById(R.id.backMenu);
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        barChart = findViewById(R.id.barChart);

        session = new SessionManager(this);
        user = session.getUserDetails();
        String ID = user.get(session.KEY_ID);
        String JU = user.get(session.KEY_JENIS_USER);

        DBBuilder();

        barEntries = new ArrayList<>();
        labelBar = new ArrayList<>();

        Call<ArrayList<ConstChart>> getChart = db.getChart(JU,ID);
        getChart.enqueue(new Callback<ArrayList<ConstChart>>() {
            @Override
            public void onResponse(Call<ArrayList<ConstChart>> call, Response<ArrayList<ConstChart>> response) {
                if(response.body().size()>0)
                {
                    for (int i = 0; i < response.body().size(); i++)
                    {
                        int jumlah;
                        String hari = response.body().get(i).getHARI();
                        if(response.body().get(i).getTOTAL_TRANSAKSI().isEmpty())
                        {
                            jumlah = 0;
                        } else {
                            jumlah = Integer.parseInt(response.body().get(i).getTOTAL_TRANSAKSI());
                        }
                        barEntries.add(new BarEntry(i, jumlah));
                        labelBar.add(hari);
                    }
                    setDataChart(labelBar, barEntries);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ConstChart>> call, Throwable t) {

            }
        });

    }

    private void setDataChart(ArrayList<String> labelBar, ArrayList<BarEntry> barEntries) {
        BarDataSet barDataSet = new BarDataSet(this.barEntries, "Transaksi Perminggu");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelBar));

        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelBar.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();

    }

    private void DBBuilder()
    {
        DB_BaseURL dbBaseURLConnector = new DB_BaseURL();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(dbBaseURLConnector.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        db = retrofit.create(MyDB.class);
    }
}