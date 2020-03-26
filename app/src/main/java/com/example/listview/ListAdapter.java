package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    Context context;
    private String[] icon_name;
    private int[] icon_image;


    public ListAdapter(Context context, String[] icon_name, int[] icon_image) {
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.icon_name = icon_name;
        this.icon_image = icon_image;
    }


    @Override
    public int getCount() {
        return icon_name.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.cat_list, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.icon = (ImageButton) convertView.findViewById(R.id.icon);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.name.setText(icon_name[position]);
        viewHolder.icon.setImageResource(icon_image[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView name;
        ImageButton icon;

    }
}
