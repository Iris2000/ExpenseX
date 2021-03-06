package com.example.listview;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    DatabaseHelper db;
    ArrayList<CatClass> incomeCats = new ArrayList<>();
    ArrayList<String> icon_name = new ArrayList<>();
    ArrayList<Integer> icon_image = new ArrayList<>();
    String username;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
        db = new DatabaseHelper(getActivity());
        incomeCats = db.getIconAndName(username, "income");
        for (int i = 0; i < incomeCats.size(); i ++) {
            String image = incomeCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_name.add(incomeCats.get(i).getCatName());
            icon_image.add(image_id);
        }
//        Log.d("catIcon", expenseCats.get(29).getCatName());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat, container, false);

        lv= (ListView) view.findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image,"income", username);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        icon_name.clear();
        icon_image.clear();
        username = getArguments().getString("username");
        db = new DatabaseHelper(getActivity());
        incomeCats = db.getIconAndName(username, "income");
        for (int i = 0; i < incomeCats.size(); i ++) {
            String image = incomeCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_name.add(incomeCats.get(i).getCatName());
            icon_image.add(image_id);
        }
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image, "income", username);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }
}
