package com.example.listview;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TransFragment extends Fragment {

    View transView;
    String username;
    String iconName;
    String drawableName;
    String memo;
    Double total;
    int year, month, day;
    DatabaseHelper db;
    ListView lv;
    String type;
    String clickedMonth;
    int clickedYear;
    int selectedMonth;
    ArrayList<RecordClass> recordList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        username = arguments.getString("username");
        clickedMonth = arguments.getString("month");
        clickedYear = arguments.getInt("year");

        if (clickedMonth != null && clickedYear != 0) {
//            Log.d("clickedMonth", clickedMonth);
//            Log.d("clickedYear", Integer.toString(clickedYear));
            switch(clickedMonth) {
                case "JAN":
                    selectedMonth = 1;
                    break;
                case "FEB":
                    selectedMonth = 2;
                    break;
                case "MAR":
                    selectedMonth = 3;
                    break;
                case "APR":
                    selectedMonth = 4;
                    break;
                case "MAY":
                    selectedMonth = 5;
                    break;
                case "JUN":
                    selectedMonth = 6;
                    break;
                case "JUL":
                    selectedMonth = 7;
                    break;
                case "AUG":
                    selectedMonth = 8;
                    break;
                case "SEP":
                    selectedMonth = 9;
                    break;
                case "OCT":
                    selectedMonth = 10;
                    break;
                case "NOV":
                    selectedMonth = 11;
                    break;
                case "DEC":
                    selectedMonth = 12;
                    break;
                default:
                    break;
            }
        } else {
            selectedMonth = 4;
            clickedYear = 2020;
        }

        db = new DatabaseHelper(getActivity());

        recordList = db.getRecord(username, clickedYear, selectedMonth);

        // Inflate the layout for this fragment
        transView = inflater.inflate(R.layout.fragment_trans, container, false);
        lv = transView.findViewById(R.id.list_view);
        RecordListAdapter adapter = new RecordListAdapter(this.getActivity(), recordList);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        FloatingActionButton fab = transView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecord();
            }
        });

        return transView;
    }

    private void addRecord() {
        Intent intent = new Intent(getActivity(), AddRecord.class);
        intent.putExtra("username", username);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2)
        {
            drawableName = data.getStringExtra("drawable_name");
            iconName = data.getStringExtra("icon_name");
            memo = data.getStringExtra("memo");
            total = data.getDoubleExtra("total", 0);
            year = data.getIntExtra("year", 0);
            month = data.getIntExtra("month", 0);
            day = data.getIntExtra("day", 0);
            String selected = data.getStringExtra("type");
//            Log.d("selected", selected);
            if (selected.equals("Add Expense")) {
                type = "expense";
            } else {
                type = "income";
            }
            Log.d("drawable_name", drawableName);
            Log.d("icon_name", iconName);
            Log.d("memo", memo);
            Log.d("total", Double.toString(total));
            Log.d("year", Integer.toString(year));
            Log.d("month", Integer.toString(month));
            Log.d("day", Integer.toString(day));
            Log.d("type", type);

            db = new DatabaseHelper(getActivity());
            Boolean saveRecord = db.saveRecord(username, drawableName, iconName, memo, total, year, month, day, type);
            if (!saveRecord) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recordList.clear();
        db = new DatabaseHelper(getActivity());
        recordList = db.getRecord(username, clickedYear, selectedMonth);
        RecordListAdapter adapter = new RecordListAdapter(this.getActivity(), recordList);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }
}