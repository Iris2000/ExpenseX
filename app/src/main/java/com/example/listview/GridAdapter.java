package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    Context context;
    String[] icon_name;
    int[] icon_image;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] icon_name, int[] icon_image) {
        this.context = context;
        this.icon_name = icon_name;
        this.icon_image = icon_image;
    }

    @Override
    public int getCount() {
        return icon_name.length;
    }

    @Override
    public Object getItem(int position) {
        return icon_name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.icon_list, null);
        }
        ImageButton imageButton = (ImageButton) gridView.findViewById(R.id.icon_image);
        TextView textView = (TextView) gridView.findViewById(R.id.icon_name);
        imageButton.setImageResource(icon_image[position]);
        imageButton.setTag(position);
        textView.setText(icon_name[position]);
        return gridView;
    }
}
