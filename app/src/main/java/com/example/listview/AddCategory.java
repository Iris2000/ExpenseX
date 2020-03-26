package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class AddCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            getSupportActionBar().setTitle("Add " + name + " Category");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent getIntent = getIntent();
                String username = getIntent.getStringExtra("username");
                Intent intent = new Intent();
                intent.putExtra("username", username);
                setResult(2, intent);
                finish();//finishing activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
