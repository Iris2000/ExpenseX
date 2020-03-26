package com.example.listview;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

public class TransFragment extends Fragment {

    private View transView;
    private TextView mDate;
    private String month;
    private int year;
    private Dialog chooseDate;
    private ToggleButton[] toggleBtn;
    private ToggleButton checkedBtn = null;
    private String[] monthName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        transView = inflater.inflate(R.layout.fragment_trans, container, false);

//        // get current month and year
//        Calendar calendar = Calendar.getInstance();
//        monthName = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
//                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
//        month = monthName[calendar.get(Calendar.MONTH)];
//        year = calendar.get(Calendar.YEAR);
//        mDate = transView.findViewById(R.id.date);
//        String text = month + " " + year;
//        mDate.setText(text);
//        mDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker(v);
//            }
//        });

        return transView;
    }

//    private void showDatePicker(View view) {
//        toggleBtn = new ToggleButton[12];
//
//        chooseDate = new Dialog(getActivity());
//        chooseDate.setContentView(R.layout.activity_choose_date);
//
//        toggleBtn[0] = chooseDate.findViewById(R.id.toggleButton1);
//        toggleBtn[1] = chooseDate.findViewById(R.id.toggleButton2);
//        toggleBtn[2] = chooseDate.findViewById(R.id.toggleButton3);
//        toggleBtn[3] = chooseDate.findViewById(R.id.toggleButton4);
//        toggleBtn[4] = chooseDate.findViewById(R.id.toggleButton5);
//        toggleBtn[5] = chooseDate.findViewById(R.id.toggleButton6);
//        toggleBtn[6] = chooseDate.findViewById(R.id.toggleButton7);
//        toggleBtn[7] = chooseDate.findViewById(R.id.toggleButton8);
//        toggleBtn[8] = chooseDate.findViewById(R.id.toggleButton9);
//        toggleBtn[9] = chooseDate.findViewById(R.id.toggleButton10);
//        toggleBtn[10] = chooseDate.findViewById(R.id.toggleButton11);
//        toggleBtn[11] = chooseDate.findViewById(R.id.toggleButton12);
//
//        for (int i = 0; i < toggleBtn.length; i++) {
//            toggleBtn[i].setOnClickListener(this);
//        }
//
//        for (int i = 0 ; i < monthName.length; i++) {
//            if (month.equals(monthName[i])) {
//                toggleBtn[i].setChecked(true);
//            }
//        }
//
//        final TextView mYear = chooseDate.findViewById(R.id.yearTextView);
//        mYear.setText(Integer.toString(year));
//        final Button mLeftArrow = chooseDate.findViewById(R.id.leftBtn);
//        final Button mRightArrow = chooseDate.findViewById(R.id.rightBtn);
//        mDate = transView.findViewById(R.id.date);
//
//        mLeftArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                year--;
//                mYear.setText(Integer.toString(year));
//                String text = month + " " + year;
//                mDate.setText(text);
//
//            }
//        });
//
//        mRightArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                year++;
//                mYear.setText(Integer.toString(year));
//                String text = month + " " + year;
//                mDate.setText(text);
//            }
//        });
//        chooseDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        chooseDate.show();
//    }
//
//    @Override
//    public void onClick(View view) {
//        ToggleButton checked = (ToggleButton) view;
//
//        for (int i = 0; i < toggleBtn.length; i++) {
//            if (toggleBtn[i].isChecked()) {
//                if (checkedBtn != null) {
//                    checkedBtn.setChecked(false);
//                }
//                checkedBtn = toggleBtn[i];
//            }
//        }
//        if (checked == checkedBtn) {
//            checkedBtn.setChecked(true);
//        }
//
//        month = checked.getText().toString();
//        mDate = transView.findViewById(R.id.date);
//        String text = month + " " + year;
//        mDate.setText(text);
//    }
}