package com.example.prateek.bimsadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderPage extends AppCompatActivity {
    private List<Order>  orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    TextView count;

    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu_page);


        Firebase.setAndroidContext(this);

        ref = new Firebase(Server.URL);
        getOrder();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OrderAdapter(orderList,this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }



    Firebase ref;

    private void getOrder(){
        //final Food food = new Food(null, null);
        Firebase objRef = ref.child("Order");

        objRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {

                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                    Object value = snapshot.child("name").getValue();
                    Object valueF = snapshot.child("number").getValue();
                    Object valueA = snapshot.child("amount").getValue();
                    Object valueAd = snapshot.child("address").getValue();
                    Object valueM = snapshot.child("mail").getValue();

                    Order order = new Order();
                    //order.setItem(snapshot.child("item").getValue());
                    order.setItem(snapshot.child("item").getValue());
                    order.setName(value.toString());
                    order.setNumber(valueF.toString());
                    order.setAmount(valueA.toString());
                    order.setAddress(valueAd.toString());
                    order.setMail(valueM.toString());
                    orderList.add(order);
                    mAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }


}
