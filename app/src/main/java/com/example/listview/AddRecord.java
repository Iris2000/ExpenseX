package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddRecord extends AppCompatActivity {
    String selected = "Add Expense";
    DatabaseHelper db;
    GridView gridView;
    String[] icon_name;
    int[] icon_image;
    ArrayList<CatClass> catList = new ArrayList<>();
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        db = new DatabaseHelper(this);
        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("username");

        // customize action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(R.layout.spinner_appbar);

            final Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.category, R.layout.spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected = spinner.getSelectedItem().toString();
                    Log.d("selected", selected);
                    setGridView();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

       setGridView();
    }

    public void setGridView() {
        // populate grid view
        if (selected.equals("Add Expense")) {
            catList = db.getIconAndName(username, "expense");
            icon_name = new String[catList.size()];
            icon_image = new int[catList.size()];
            for (int i = 0; i < catList.size(); i ++) {
                icon_name[i] = catList.get(i).getCatName();
                String image = catList.get(i).getCatIcon();
                int image_id = getResources().getIdentifier(image, "drawable", getPackageName());
                icon_image[i] = image_id;
                Log.d("icon_name", icon_name[i]);
                Log.d("icon_image", Integer.toString(icon_image[i]));
            }
        } else if (selected.equals("Add Income")) {
            catList = db.getIconAndName(username, "income");
            icon_name = new String[catList.size()];
            icon_image = new int[catList.size()];
            for (int i = 0; i < catList.size(); i ++) {
                icon_name[i] = catList.get(i).getCatName();
                String image = catList.get(i).getCatIcon();
                int image_id = getResources().getIdentifier(image, "drawable", getPackageName());
                icon_image[i] = image_id;
                Log.d("icon_name", icon_name[i]);
                Log.d("icon_image", Integer.toString(icon_image[i]));
            }
        }

        gridView = findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(this, icon_name, icon_image);
        gridView.setAdapter(gridAdapter);
    }
}
