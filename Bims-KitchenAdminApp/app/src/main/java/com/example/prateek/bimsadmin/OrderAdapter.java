package com.example.prateek.bimsadmin;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RajeevPC on 2/18/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private static List<Order> orderList;
    private Context context ;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number,email,amount,address;
        public RelativeLayout con;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Oname);
            number = (TextView) view.findViewById(R.id.Onumber);
//            email = (TextView)view.findViewById(R.id.Oemail);
//            amount  =(TextView)view.findViewById(R.id.Oamount);
//            address  =(TextView)view.findViewById(R.id.Oaddress);

            con = (RelativeLayout) view.findViewById(R.id.container);



            con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Order currentOrder = orderList.get(pos);
                    Intent intent = new Intent(context,DetailsActivity.class);
                    intent.putExtra("email",currentOrder.getMail());
                    intent.putExtra("address",currentOrder.getAddress());
                    intent.putExtra("amount",currentOrder.getAmount());

                    intent.putExtra("items",currentOrder.getItems().toString());
                    context.startActivity(intent);

                }
            });

        }
    }


    public OrderAdapter(List<Order> orderList,Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item1, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Order order = orderList.get(position);
        final ArrayList<HashMap<String,String>> items = (ArrayList<HashMap<String, String>>) order.getItems();


        final ArrayList<Order.Item> _it = new ArrayList<>();
        for(int i=0;i<items.size();i++){
           HashMap<String,String> f = items.get(i);
            _it.add(new Order.Item(f.get("food"),f.get("price"),f.get("quantity")));

        }

        holder.name.setText(order.getName());
        holder.number.setText(order.getNumber());
//        holder.email.setText(order.getMail());
//        holder.amount.setText(order.getAmount());
//        holder.address.setText(order.getAddress());

        ((MyViewHolder) holder).con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order currentOrder = orderList.get(position);
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("email",currentOrder.getMail());
                intent.putExtra("amount",currentOrder.getAmount());
                intent.putExtra("address",currentOrder.getAddress());
                intent.putParcelableArrayListExtra("items",_it);
                context.startActivity(intent);

            }
        });
        Log.d("items " + items,"item");


    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
