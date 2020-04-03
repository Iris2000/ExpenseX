package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PieChart extends AppCompatActivity {
    private ToggleButton[] toggleBtn;
    private Dialog chooseDate;
    String username;
    private String[] monthName;
    private String month;
    private TextView mDate;
    private int year;
    private ToggleButton checkedBtn = null;
    String selected = "Expense Chart";
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("username");

        // get current month and year
        Calendar calendar = Calendar.getInstance();
        monthName = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        month = monthName[calendar.get(Calendar.MONTH)];
        year = calendar.get(Calendar.YEAR);
        mDate = findViewById(R.id.date);
        mDate.setText(month + " " + year);

        // customize action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(R.layout.spinner_appbar);

            final Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.chart, R.layout.spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected = spinner.getSelectedItem().toString();
                    setupPieChart();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        setupPieChart();
    }

    public void showDatePicker(View view) {
        toggleBtn = new ToggleButton[12];

        chooseDate = new Dialog(this);
        chooseDate.setContentView(R.layout.activity_choose_date);

        toggleBtn[0] = chooseDate.findViewById(R.id.toggleButton1);
        toggleBtn[1] = chooseDate.findViewById(R.id.toggleButton2);
        toggleBtn[2] = chooseDate.findViewById(R.id.toggleButton3);
        toggleBtn[3] = chooseDate.findViewById(R.id.toggleButton4);
        toggleBtn[4] = chooseDate.findViewById(R.id.toggleButton5);
        toggleBtn[5] = chooseDate.findViewById(R.id.toggleButton6);
        toggleBtn[6] = chooseDate.findViewById(R.id.toggleButton7);
        toggleBtn[7] = chooseDate.findViewById(R.id.toggleButton8);
        toggleBtn[8] = chooseDate.findViewById(R.id.toggleButton9);
        toggleBtn[9] = chooseDate.findViewById(R.id.toggleButton10);
        toggleBtn[10] = chooseDate.findViewById(R.id.toggleButton11);
        toggleBtn[11] = chooseDate.findViewById(R.id.toggleButton12);

        for (int i = 0 ; i < monthName.length; i++) {
            if (month.equals(monthName[i])) {
                toggleBtn[i].setChecked(true);
            }
        }

        final TextView mYear = chooseDate.findViewById(R.id.yearTextView);
        mYear.setText(Integer.toString(year));
        final Button mLeftArrow = chooseDate.findViewById(R.id.leftBtn);
        final Button mRightArrow = chooseDate.findViewById(R.id.rightBtn);
        mDate = findViewById(R.id.date);

        mLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year--;
                mYear.setText(Integer.toString(year));
                String text = month + " " + year;
                mDate.setText(text);

            }
        });

        mRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year++;
                mYear.setText(Integer.toString(year));
                String text = month + " " + year;
                mDate.setText(text);
            }
        });
        chooseDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseDate.show();
    }

    public void onMonthClicked(View view) {
        ToggleButton checked = (ToggleButton) view;

        for (int i = 0; i < toggleBtn.length; i++) {
            if (toggleBtn[i].isChecked()) {
                if (checkedBtn != null) {
                    checkedBtn.setChecked(false);
                }
                checkedBtn = toggleBtn[i];
            }
        }
        if (checked == checkedBtn) {
            checkedBtn.setChecked(true);
        }

        month = checked.getText().toString();
        mDate = findViewById(R.id.date);
        String text = month + " " + year;
        mDate.setText(text);

        // update pie chart
        setupPieChart();
    }

    public void setupPieChart() {
        List<PieEntry> pieEntries;
        String type;
        if (selected.equals("Expense Chart")) {
            type = "expense";
        } else {
            type = "income";
        }

        db = new DatabaseHelper(this);
        pieEntries = db.getChartData2(username, month, year, type);
        PieDataSet dataSet = new PieDataSet(pieEntries, type);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        Double totalValue = db.getTotalValue(username, month, year, type);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        data.setValueFormatter(new PercentFormatter());
        // get the chart
        com.github.mikephil.charting.charts.PieChart chart = findViewById(R.id.chart);
        chart.setData(data);
        chart.setRotationEnabled(true);
        chart.setUsePercentValues(true);
        chart.setEntryLabelTextSize(15);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setDrawEntryLabels(true);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setCenterTextSize(20f);
        chart.setDrawHoleEnabled(true);
        if (type.equals("expense")) {
            chart.setCenterText("Expense\nRM" + totalValue);
        } else {
            chart.setCenterText("Income\nRM" + totalValue);
        }
        chart.getDescription().setEnabled(false);
        chart.setHoleColor(Color.WHITE);
        chart.animateY(1000);
        chart.invalidate();
    }
}
