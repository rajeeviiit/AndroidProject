package com.example.prateek.bimsadmin;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private ArrayList<Order.Item> items;
    public TextView tvEmail,tvAddress,tvAmount;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

       lv = (ListView)findViewById(R.id.order_list);
        tvEmail = (TextView) findViewById(R.id.tvemail);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAmount = (TextView) findViewById(R.id.tvAmount);

        String email = getIntent().getExtras().getString("email");
        String amount = getIntent().getExtras().getString("amount");
        String address = getIntent().getExtras().getString("address");
        items = getIntent().getExtras().getParcelableArrayList("items");
//        Toast.makeText(this, "Email " + email, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Amount " + amount, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Address " + address, Toast.LENGTH_SHORT).show();
//       // Toast.makeText(this, "Items " + items, Toast.LENGTH_SHORT).show();
//        Log.d("Details", " items " + items.size());

        OrderListAdapter adapter = new OrderListAdapter(this,items);

        lv.setAdapter(adapter);
        tvEmail.setText(email);
        tvAddress.setText(address);
        tvAmount.setText(amount);

    }
}
