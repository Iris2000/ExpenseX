package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class CatActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    String username;
    int position;
    DatabaseHelper db;
    MyAdapter adapter;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("username");

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Expense"));
        tabLayout.addTab(tabLayout.newTab().setText("Income"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        Log.d("username", username);
        adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), username, "hi");
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void OnAddButtonClicked(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        if (position == 0) {
            type = "Expense";
        } else if (position == 1) {
            type = "Income";
        }
        intent.putExtra("name", type);
        intent.putExtra("username", username);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2)
        {
            username = data.getStringExtra("username");
            String drawableName = data.getStringExtra("drawableName");
            String catName = data.getStringExtra("catName");
            if (!drawableName.equals("") && !catName.equals("")) {
                Log.d("drawableName", drawableName);
                Log.d("catName", catName);
                db = new DatabaseHelper(this);
                Boolean saveCat = db.saveCategory(drawableName, catName, username, position);
                if (!saveCat) {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
