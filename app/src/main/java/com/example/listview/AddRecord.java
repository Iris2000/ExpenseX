package com.example.listview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddRecord extends AppCompatActivity {
    String selected = "Add Expense";
    DatabaseHelper db;
    GridView gridView;
    String[] icon_name;
    int[] icon_image;
    ArrayList<CatClass> catList = new ArrayList<>();
    String username;
    Button num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, mDecimal, mDate, mPlus, mMinus, mClear, mConfirm;
    ImageButton mIcon;
    EditText mMemo;
    TextView mTotal;
    String drawableName;
    String textViewTotal;
    String process;
    Boolean checkMinus = true;
    Boolean checkPlus = true;
    Boolean checkDecimal = true;
    double value1 = Double.NaN, value2;
    String iconName;
    String memo = "";
    int id = 0;
    double total;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        num_0 = findViewById(R.id.num0);
        num_1 = findViewById(R.id.num1);
        num_2 = findViewById(R.id.num2);
        num_3 = findViewById(R.id.num3);
        num_4 = findViewById(R.id.num4);
        num_5 = findViewById(R.id.num5);
        num_6 = findViewById(R.id.num6);
        num_7 = findViewById(R.id.num7);
        num_8 = findViewById(R.id.num8);
        num_9 = findViewById(R.id.num9);

        mDecimal = findViewById(R.id.decimal);
        mDate = findViewById(R.id.date);
        mPlus = findViewById(R.id.plus);
        mMinus = findViewById(R.id.minus);
        mClear = findViewById(R.id.clear);
        mConfirm = findViewById(R.id.confirm);

        mIcon = findViewById(R.id.icon);
        mMemo = findViewById(R.id.memo);
        mTotal = findViewById(R.id.total);

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

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // set calculator btn listener
        num_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "0");
            }
        });

        num_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "1");
            }
        });

        num_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "2");
            }
        });

        num_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "3");
            }
        });

        num_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "4");
            }
        });

        num_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "5");
            }
        });

        num_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "6");
            }
        });

        num_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "7");
            }
        });

        num_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "8");
            }
        });

        num_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMinus = false;
                checkPlus = false;
                checkDecimal = false;
                process = mTotal.getText().toString();
                if (process.contains("+") || process.contains("-")) {
                    mConfirm.setText("=");
                }
                mTotal.setText(process + "9");
            }
        });

        mDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDecimal) {
                    process = mTotal.getText().toString();
                    mTotal.setText(process + ".");
                    checkDecimal = true;
                    checkPlus = true;
                    checkMinus = true;
                }
            }
        });

        mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPlus) {
                    compute();
                    mTotal.setText(String.format("%.1f", value1) + "+");
                    checkPlus = true;
                    checkMinus = true;
                    checkDecimal = true;
                }
            }
        });

        mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkMinus) {
                    compute();
                    mTotal.setText(String.format("%.1f", value1) + "-");
                    checkMinus = true;
                    checkPlus = true;
                    checkDecimal = true;
                }
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1 = Double.NaN;
                value2 = Double.NaN;
                mTotal.setText(null);
                mConfirm.setText("✔");
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirm.getText().toString().equals("=")) {
                    compute();
                    mTotal.setText(String.format("%.1f", value1));
                    mConfirm.setText("✔");
                    value1 = Double.NaN;
                    value2 = Double.NaN;
                } else {
                    textViewTotal = mTotal.getText().toString();
                    if (textViewTotal.equals("")) {
                        Toast.makeText(getApplicationContext(),"Please enter the amount", Toast.LENGTH_SHORT).show();
                    } else {
                        memo = mMemo.getText().toString();
                        iconName = icon_name[id];
                        total = Double.parseDouble(mTotal.getText().toString());
//                        Log.d("iconName", iconName);
//                        Log.d("memo", memo);
//                        Log.d("drawableName", drawableName);
//                        Log.d("total", Double.toString(total));
                        Intent intent = new Intent();
                        intent.putExtra("icon_name", iconName);
                        intent.putExtra("memo", memo);
                        intent.putExtra("drawable_name", drawableName);
                        intent.putExtra("total", total);
                        intent.putExtra("year", year);
                        intent.putExtra("month", month);
                        intent.putExtra("day", day);
                        intent.putExtra("type", selected);
                        setResult(2, intent);
                        finish();
                    }
                }
            }
        });
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
            }
        }

        gridView = findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(this, icon_name, icon_image);
        gridView.setAdapter(gridAdapter);
        mIcon.setImageResource(icon_image[0]);
        drawableName = catList.get(0).getCatIcon();
        iconName = catList.get(0).getCatName();
    }

    public void onImageButtonClicked(View view) {
        Drawable getDrawable = ((ImageButton)view).getDrawable();
        mIcon.setImageDrawable(getDrawable);
        id = (Integer) view.getTag();
        drawableName = catList.get(id).getCatIcon();
//        Log.d("drawableName", drawableName);
    }

    private void compute(){
        if(!Double.isNaN(value1)){
            process = mTotal.getText().toString();
            if (process.contains("+")) {
                process = process.substring(process.indexOf("+")+1);
                process = process.trim();
                value2 = Math.abs(Double.parseDouble(process));
                mConfirm.setText("✔");
                value1 = Math.abs(value1 + value2);
            } else {
                process = process.substring(process.indexOf("-")+1);
                process = process.trim();
                value2 = Math.abs(Double.parseDouble(process));
                mConfirm.setText("✔");
                value1 = Math.abs(value1 - value2);
            }
        }
        else{
            value1 = Double.parseDouble(mTotal.getText().toString());
        }
    }

    public void processDatePickerResult(int year, int month, int day) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (day_string + "/" + month_string + "/" + year_string);
        mDate.setText(dateMessage);
    }
}
