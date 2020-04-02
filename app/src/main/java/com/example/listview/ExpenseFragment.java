package com.example.listview;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    DatabaseHelper db;
    ArrayList<CatClass> expenseCats = new ArrayList<>();
    ArrayList<String> icon_name = new ArrayList<>();
    ArrayList<Integer> icon_image = new ArrayList<>();
    String username;
    ListView lv;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
//        Log.d("username", username);
        db = new DatabaseHelper(getActivity());
        expenseCats = db.getIconAndName(username, "expense");

        for (int i = 0; i < expenseCats.size(); i ++) {
            String image = expenseCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_name.add(expenseCats.get(i).getCatName());
            icon_image.add(image_id);
        }
//        Log.d("catIcon", expenseCats.get(29).getCatName());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat, container, false);
        lv = (ListView) view.findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image, "expense", username);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        icon_name.clear();
        icon_image.clear();
        db = new DatabaseHelper(getActivity());
        expenseCats = db.getIconAndName(username, "expense");
        for (int i = 0; i < expenseCats.size(); i ++) {
            String image = expenseCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_name.add(expenseCats.get(i).getCatName());
            icon_image.add(image_id);
        }
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image, "expense", username);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

}
