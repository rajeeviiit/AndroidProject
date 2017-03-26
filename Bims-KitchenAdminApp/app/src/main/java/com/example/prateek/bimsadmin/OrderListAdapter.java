package com.example.prateek.bimsadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by RajeevPC on 2/28/2017.
 */

public class OrderListAdapter extends ArrayAdapter<Order.Item> {

    private ArrayList<Order.Item> items;
    private TextView title,title1,title2;
    private Context context;

    public OrderListAdapter(Context context, ArrayList<Order.Item> i) {
        super(context,0);
        this.context = context;
        items = new ArrayList<>();
        this.items = i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null){
            v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        }

        title = (TextView) v.findViewById(R.id.title);
        title1 = (TextView) v.findViewById(R.id.title1);
        title2 = (TextView) v.findViewById(R.id.title2);
        title.setText(items.get(position).food);
        title1.setText(items.get(position).price);
        title2.setText(items.get(position).quantity);

        return v;
    }

    @Override
    public int getCount() {
        return items.size();
    }


}
