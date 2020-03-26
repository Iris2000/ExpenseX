package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class CatActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String username;
    int position;

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
        MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(), username);
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
            intent.putExtra("name", "Expense");
        } else if (position == 1) {
            intent.putExtra("name", "Income");
        }
        startActivity(intent);
    }
}
