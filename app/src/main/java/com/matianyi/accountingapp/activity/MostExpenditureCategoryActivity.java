package com.matianyi.accountingapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.matianyi.accountingapp.R;
import com.matianyi.accountingapp.util.DateUtil;
import com.matianyi.accountingapp.util.GlobalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostExpenditureCategoryActivity extends AppCompatActivity {

    Map<String, Double> mostCategory = new HashMap<>();

    public static final String TAG = "MECA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(MostExpenditureCategoryActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();

        mostCategory = GlobalUtil.getInstance().databaseHelper.getMostExpenditureCategory();

        for (Map.Entry<String, Double> entry : mostCategory.entrySet()) {
            data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
        }

        if (data.isEmpty()){
            Toast.makeText(getApplicationContext(), "??????????????????????????????????????????",Toast.LENGTH_SHORT).show();
            finish();
        }

        pie.data(data);

        pie.animation(true);

        pie.title("??????????????????");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("???????????? " + DateUtil.getFormattedDate())
                .padding(0d, 0d, 60d, 0d);

        pie.legend()
                .position("center-center")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }
}