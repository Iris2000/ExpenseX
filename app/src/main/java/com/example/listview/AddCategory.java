package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddCategory extends AppCompatActivity {

    ImageButton mIconButton;
    EditText mIconName;
    String drawableName;
    DatabaseHelper db;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mIconButton = findViewById(R.id.icon);
        mIconName = findViewById(R.id.cat_name);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            getSupportActionBar().setTitle("Add " + name + " Category");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_cat_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent getIntent = getIntent();
        String username = getIntent.getStringExtra("username");
        Intent intent = new Intent();
        intent.putExtra("username", username);
        switch (item.getItemId()) {
            case android.R.id.home:
                intent.putExtra("drawableName", "");
                intent.putExtra("catName", "");
                setResult(2, intent);
                finish();//finishing activity
                return true;
            case R.id.confirm:
                String catName = mIconName.getText().toString();
                if (catName.equals("")) {
                    Toast.makeText(this,"Category Name is empty", Toast.LENGTH_SHORT).show();
                } else {
                    db = new DatabaseHelper(this);
                    Boolean checkCatName = db.checkCatName(username, catName);
                    if (checkCatName) {
                        Toast.makeText(this,"Similar category name already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("drawableName", drawableName);
                        intent.putExtra("catName", catName);
                        setResult(2, intent);
                        finish();//finishing activity
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onImageButtonClicked(View view) {
        Drawable getDrawable = ((ImageButton)view).getDrawable();
        mIconButton.setImageDrawable(getDrawable);

        drawableName = view.getResources().getResourceEntryName(view.getId());
    }
}
