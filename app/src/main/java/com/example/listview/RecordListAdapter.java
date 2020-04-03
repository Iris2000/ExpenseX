package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class RecordListAdapter extends BaseAdapter {

    Context context;
    ArrayList<RecordClass> recordList;
    DecimalFormat df;

    public RecordListAdapter(Context context, ArrayList<RecordClass> recordList) {
        this.context = context;
        this.recordList = recordList;
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
        notifyDataSetChanged();
        ViewHolder viewHolder;
        df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

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
        viewHolder.icon.setTag(recordList.get(position).getRecordId());
        viewHolder.name.setText(recordList.get(position).getCatName());
        viewHolder.memo.setText(recordList.get(position).getMemo());
        viewHolder.date.setText(date);
        viewHolder.total.setText("RM" + df.format(recordList.get(position).getTotal()));
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
