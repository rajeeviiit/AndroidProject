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
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends AppCompatActivity {
    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;


    TextView count;

    private FoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);


        Firebase.setAndroidContext(this);

        ref = new Firebase(Server.URL);
        getNonVegMenu();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new FoodAdapter(foodList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, final int position) {
//
//            }
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }

    Firebase ref;

    private void getNonVegMenu(){
        //final Food food = new Food(null, null);
        Firebase objRef = ref.child("Menu");
        objRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());

                    foodList.add(food);
                    mAdapter.notifyDataSetChanged();
                    Log.d("food "+value.toString(), "price "+valueF.toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }
}
