package com.example.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> icon_name;
    ArrayList<Integer> icon_image;
    String type;
    DatabaseHelper db;
    String username;

    public ListAdapter(Context context, ArrayList<String> icon_name, ArrayList<Integer> icon_image, String type, String username) {
        this.context = context;
        this.icon_name = icon_name;
        this.icon_image = icon_image;
        this.type = type;
        db = new DatabaseHelper(context);
        this.username = username;
    }


    @Override
    public int getCount() {
        return icon_name.size();
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

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.cat_list, parent, false);
            viewHolder.listView = (RelativeLayout) convertView.findViewById(R.id.list_view);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.icon = (ImageButton) convertView.findViewById(R.id.icon);
            viewHolder.deleteBtn = (ImageView) convertView.findViewById(R.id.delete_button);
            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("delete", "button clicked");
                    View parentRow = (View) v.getParent();
                    ListView lv = (ListView) parentRow.getParent();
                    final int position = lv.getPositionForView(parentRow);
//                    Log.d("position", Integer.toString(position));
                    Boolean deleteItem = db.deleteItem(type, position+1, username);
                    if (deleteItem) {
                        icon_name.remove(position);
                        icon_image.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(icon_name.get(position));
        viewHolder.icon.setImageResource(icon_image.get(position));
        return convertView;
    }

    private static class ViewHolder {

        TextView name;
        ImageButton icon;
        ImageView deleteBtn;
        RelativeLayout listView;

    }
}
