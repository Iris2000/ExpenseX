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
    String[] icon_name;
    int[] icon_image;
    String username;
    ListView lv;

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        username = getArguments().getString("username");
        db = new DatabaseHelper(getActivity());
        incomeCats = db.getIconAndName(username, "income");
        icon_name = new String[incomeCats.size()];
        icon_image = new int[incomeCats.size()];
        for (int i = 0; i < incomeCats.size(); i ++) {
            icon_name[i] = incomeCats.get(i).getCatName();
            String image = incomeCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_image[i] = image_id;
//            Log.d("image_id", String.valueOf(image_id));
        }
//        Log.d("catIcon", expenseCats.get(29).getCatName());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat, container, false);

        lv= (ListView) view.findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image);
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
        username = getArguments().getString("username");
        db = new DatabaseHelper(getActivity());
        incomeCats = db.getIconAndName(username, "income");
        icon_name = new String[incomeCats.size()];
        icon_image = new int[incomeCats.size()];
        for (int i = 0; i < incomeCats.size(); i ++) {
            icon_name[i] = incomeCats.get(i).getCatName();
            String image = incomeCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_image[i] = image_id;
        }
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image);
        lv.setAdapter(adapter);
    }
}
