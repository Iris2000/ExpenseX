package com.example.listview;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(context);
//            convertView = inflater.inflate(R.layout.icon_list, parent,false);
////            view = new View(context);
////            view = layoutInflater.inflate(R.layout.icon_list, null);
//            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.icon);
//            viewHolder.textView = (TextView) convertView.findViewById(R.id.icon_name);
//            convertView.setTag(viewHolder);
//        }
//        else {
//            viewHolder = (GridAdapter.ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.imageButton.setImageResource(icon_image[position]);
//        viewHolder.textView.setText(icon_name[position]);
//        return convertView;
        View gridView = convertView;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.icon_list, null);
        }
        ImageButton imageButton = (ImageButton) gridView.findViewById(R.id.icon_image);
        TextView textView = (TextView) gridView.findViewById(R.id.icon_name);
        imageButton.setImageResource(icon_image[position]);
        textView.setText(icon_name[position]);
        return gridView;
    }

    private static class ViewHolder {

        TextView textView;
        ImageButton imageButton;

    }
}
