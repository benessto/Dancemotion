package mum.dancemotion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class StatistikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        //Einträge für BarChart
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        //Labels für die Chart
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Januar");
        labels.add("Februar");
        labels.add("März");
        labels.add("April");
        labels.add("Mai");
        labels.add("Juni");

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        //BarChart chart = new BarChart(context);
        //setContentView(chart);
        BarChart chart = (BarChart) findViewById(R.id.chart);
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        chart.setDescription("# of times Alice called Bob");
        Toast.makeText(getBaseContext(), "StatistikActivity wurde gestartet!", Toast.LENGTH_LONG).show();

    }
}
