package com.example.ui1.UI;

import com.example.ui1.API.*;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ui1.R;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Stats extends AppCompatActivity {
    private TextView localtotal,localactive,localrecovered,localdeaths;
    private TextView globaltotal,globalactive,globalrecovered,globaldeaths;
    private PieChart pieChart;

    private Button btnHome;

    private List<ModelClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stats);

        list=new ArrayList<>();

        init();

        APIUtilities.getAPIInterface().getLocalData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                list.addAll(response.body());

                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getCountry().equals("Sri Lanka")) {
                        int localTotal = Integer.parseInt(list.get(i).getCases());
                        int localActive = Integer.parseInt(list.get(i).getActive());
                        int localRecovered = Integer.parseInt(list.get(i).getRecovered());
                        int localDeaths = Integer.parseInt(list.get(i).getDeaths());

                        localtotal.setText(NumberFormat.getInstance().format(localTotal));
                        localactive.setText(NumberFormat.getInstance().format(localActive));
                        localrecovered.setText(NumberFormat.getInstance().format(localRecovered));
                        localdeaths.setText(NumberFormat.getInstance().format(localDeaths));

                        pieChart.addPieSlice(new PieModel("Total", localTotal, Color.parseColor("#FFB701")));
                        pieChart.addPieSlice(new PieModel("Active", localActive, Color.parseColor("#008F06")));
                        pieChart.addPieSlice(new PieModel("Recovered", localRecovered, Color.parseColor("#FF3700B3")));
                        pieChart.addPieSlice(new PieModel("Deaths", localDeaths, Color.parseColor("#f55c47")));
                        pieChart.startAnimation();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                Toast.makeText(Stats.this,"Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        getGlobalData();

        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });

    }

    private void getGlobalData() {
        APIUtilities.getAPIInterface1().getGlobalData().enqueue(new Callback<ModelClass1>() {
            @Override
            public void onResponse(Call<ModelClass1> call, Response<ModelClass1> response) {
                ModelClass1 modal = response.body();

                int globalTotal = Integer.parseInt(modal.getCases());
                int globalActive = Integer.parseInt(modal.getActive());
                int globalRecovered = Integer.parseInt(modal.getRecovered());
                int globalDeaths = Integer.parseInt(modal.getDeaths());

                globaltotal.setText(NumberFormat.getInstance().format(globalTotal));
                globalactive.setText(NumberFormat.getInstance().format(globalActive));
                globalrecovered.setText(NumberFormat.getInstance().format(globalRecovered));
                globaldeaths.setText(NumberFormat.getInstance().format(globalDeaths));

            }

            @Override
            public void onFailure(Call<ModelClass1> call, Throwable t) {

            }
        });
    }


    private void init(){
        localtotal=findViewById(R.id.localtotal);
        localactive=findViewById(R.id.localactive);
        localrecovered=findViewById(R.id.localrecovered);
        localdeaths=findViewById(R.id.localdeaths);
        globaltotal=findViewById(R.id.globlatotal);
        globalactive=findViewById(R.id.globalactive);
        globalrecovered=findViewById(R.id.globalrecovered);
        globaldeaths=findViewById(R.id.globaldeaths);
        pieChart=findViewById(R.id.piechart);
    }

    public void openHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Stats.this,Home.class);
        startActivity(intent);

    }
}

