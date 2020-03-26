package com.example.listview;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    DatabaseHelper db;
    ArrayList<CatClass> expenseCats = new ArrayList<>();
    String[] icon_name;
    int[] icon_image;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = getArguments().getString("username");
//        Log.d("username", username);
        db = new DatabaseHelper(getActivity());
        expenseCats = db.getIconAndName(username, "expense");
        icon_name = new String[expenseCats.size()];
        icon_image = new int[expenseCats.size()];
        for (int i = 0; i < expenseCats.size(); i ++) {
            icon_name[i] = expenseCats.get(i).getCatName();
            String image = expenseCats.get(i).getCatIcon();
            int image_id = getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
            icon_image[i] = image_id;
//            Log.d("image_id", String.valueOf(image_id));
        }
//        Log.d("catIcon", expenseCats.get(29).getCatName());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        ListView lv= (ListView) view.findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(this.getActivity(),icon_name,icon_image);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putBoolean("MyBoolean", true);
//        savedInstanceState.putDouble("myDouble", 1.9);
//        savedInstanceState.putInt("MyInt", 1);
//        savedInstanceState.putString("MyString", "Welcome back to Android");
//        // etc.
//    }

}
