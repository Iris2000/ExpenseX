package com.example.listview;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TransFragment extends Fragment {

    private View transView;
    String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        username = arguments.getString("username");

        // Inflate the layout for this fragment
        transView = inflater.inflate(R.layout.fragment_trans, container, false);

        FloatingActionButton fab = transView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecord();
            }
        });

        return transView;
    }

    public void addRecord() {
        Intent intent = new Intent(getActivity(), AddRecord.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}