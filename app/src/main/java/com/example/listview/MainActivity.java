package com.example.listview;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements
                NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    Toolbar mToolbar;
    TextView mUsername;
    TextView mUserEmail;
    SharedPreferences sp;
    private TextView mDate;
    private String month;
    private int year;
    private Dialog chooseDate;
    private ToggleButton[] toggleBtn;
    private ToggleButton checkedBtn = null;
    private String[] monthName;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        username = sp.getString("username", "");
        String email = sp.getString("email", "");
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        TransFragment tf = new TransFragment();
        tf.setArguments(bundle);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            ft.replace(R.id.fragment_container, tf).commit();
        }

        // get current month and year
        Calendar calendar = Calendar.getInstance();
        monthName = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        month = monthName[calendar.get(Calendar.MONTH)];
        year = calendar.get(Calendar.YEAR);
        mDate = findViewById(R.id.date);
        mDate.setText(month + " " + year);

        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        // get username and email
        mUsername = header.findViewById(R.id.username);
        mUserEmail = header.findViewById(R.id.userEmail);
        mUsername.setText(username);
        mUserEmail.setText(email);
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.cat:
                Toast.makeText(getApplicationContext(), "Category", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, CatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                break;
            case R.id.logout:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                sp.edit().clear().apply();
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }
}
