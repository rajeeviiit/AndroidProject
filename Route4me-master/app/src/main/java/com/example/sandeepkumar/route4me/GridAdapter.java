package com.example.sandeepkumar.route4me;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Ravi kumar patel on 10/10/2016.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    ImageView cellImage;
    private ArrayList<Integer> pathValues;


    public GridAdapter(Context context) {
        this.mContext = context;
        pathValues = new ArrayList<>();
    }

    public void setPathItem(int pos, int value) {
        pathValues.set(pos, value);

        notifyDataSetChanged();

    }



    public void setPathValues(ArrayList<Integer> values) {
        this.pathValues = values;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pathValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View temp = view;
        if (temp == null) {
            temp = LayoutInflater.from(mContext).inflate(R.layout.cell_layout, null, false);
        }

        cellImage = (ImageView) temp.findViewById(R.id.cell_item);


        if(pathValues.get(pos)==Constants.ITEM_TYPE_BANNED){
            Log.d("SHORTEST", "BANNED AT " + pos);
            cellImage.setImageResource(R.drawable.banned);

        }
        if(pathValues.get(pos)==Constants.ITEM_TYPE_DESTINATION){
            Log.d("SHORTEST", "dest AT " + pos);
            cellImage.setImageResource(R.drawable.destination);

        }
        if(pathValues.get(pos)==Constants.ITEM_TYPE_INTERMEDIATE_ITEM){
            Log.d("SHORTEST", "intermediate AT " + pos);
            cellImage.setImageResource(R.drawable.intermediate);
        }

        if(pathValues.get(pos)==Constants.ITEM_TYPE_INTERMEDIATE_ITEM1){
            Log.d("SHORTEST", "intermediate AT " + pos);
            cellImage.setImageResource(R.drawable.second);
        }

        if(pathValues.get(pos)==Constants.ITEM_TYPE_SOURCE){
            Log.d("SHORTEST", "source AT " + pos);
            cellImage.setImageResource(R.drawable.source);
        }
        if(pathValues.get(pos)==Constants.ITEM_TYPE_USER_SOURCE){
            Log.d("SHORTEST", "USER SOURCE AT " +pos);
            cellImage.setImageResource(R.drawable.source);

        }

        return temp;
    }
}