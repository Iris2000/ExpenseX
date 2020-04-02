package com.example.listview;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    Context context;
//    String icon_name, icon_image, type;
//    double total;
//    int month, day;
    ArrayList<RecordClass> recordList;

    public RecordListAdapter(Context context, ArrayList<RecordClass> recordList) {
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.recordList = recordList;
//        this.icon_name = icon_name;
//        this.icon_image = icon_image;
//        this.type = type;
//        this.total = total;
//        this.month = month;
//        this.day = day;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.record_list, parent, false);
            viewHolder.listView = (RelativeLayout) convertView.findViewById(R.id.list_view);
            viewHolder.icon = (ImageButton) convertView.findViewById(R.id.icon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.memo = (TextView) convertView.findViewById(R.id.memo);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.total = (TextView) convertView.findViewById(R.id.total);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String date = recordList.get(position).getDay() + "/" + recordList.get(position).getMonth() + "/" +
                        recordList.get(position).getYear();
        String type = recordList.get(position).getType();
        viewHolder.icon.setImageResource(recordList.get(position).getCatIcon());
        viewHolder.name.setText(recordList.get(position).getCatName());
        viewHolder.memo.setText(recordList.get(position).getMemo());
        viewHolder.date.setText(date);
        viewHolder.total.setText(Double.toString(recordList.get(position).getTotal()));
        if (type.equals("expense")) {
            viewHolder.total.setTextColor(ContextCompat.getColor(context, R.color.expense));
        } else {
            viewHolder.total.setTextColor(ContextCompat.getColor(context, R.color.income));
        }
        return convertView;
    }


    private static class ViewHolder {
        ImageButton icon;
        TextView name;
        TextView memo;
        TextView date;
        TextView total;
        RelativeLayout listView;
    }
}
