package com.example.prateek.bimsapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SetQuantity extends AppCompatActivity {

    TextView itemName, itemPrice, itemQuantity, priceHere, amount;
    ImageView itemImage;
    FoodQuantity foodQuantity = new FoodQuantity();
    ImageView upCount,downCount;
    String foodItemName, foodItemPrice, foodItemUrl;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_quantity);


        itemName = (TextView)findViewById(R.id.textView6);
        itemPrice = (TextView)findViewById(R.id.textView7);
        itemImage = (ImageView) findViewById(R.id.imageView4);
        itemQuantity = (TextView)findViewById(R.id.foodItemQuant);
        priceHere = (TextView)findViewById(R.id.priceHere);
        amount = (TextView)findViewById(R.id.amountNow);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        foodItemName = extras.getString("foodItemName");
        foodItemPrice = extras.getString("foodItemPrice");
        foodItemUrl = extras.getString("foodItemUrl");

        Log.d("hadf", "adjf  "+foodItemUrl);


        Picasso.with(this)
                .load(foodItemUrl)
                .into(itemImage);

        // itemName.setText(foodItemName);
        itemPrice.setText(foodItemPrice +" \u20B9");
        priceHere.setText(foodItemPrice);

        amount.setText("0" +" \u20B9");

        itemQuantity.setText("0");
        upCount = (ImageView) findViewById(R.id.upCount);
        downCount = (ImageView) findViewById(R.id.downCount);
        final int price = Integer.valueOf(foodItemPrice);

        upCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int s = Integer.parseInt(itemQuantity.getText().toString());
                s++;
                itemQuantity.setText(Integer.toString(s));
                amount.setText(Integer.toString(s*price)+" \u20B9");
            }
        });

        downCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int s = Integer.parseInt(itemQuantity.getText().toString());
                if(s>0) {
                    s--;
                    itemQuantity.setText(Integer.toString(s));
                }
                amount.setText(Integer.toString(s*price)+" \u20B9");
            }
        });


    }

    public void cancelDialog(View view) {
        finish();
    }

    public void gotOrder(View view) {

        if(itemQuantity.getText().toString().equals("0")){
            finish();
        }else{
        Log.d("kjdf", "lakjsd"+ itemQuantity.getText());
        foodQuantity.setFood(foodItemName);
        foodQuantity.setQuantity(itemQuantity.getText().toString());
        foodQuantity.setPrice(foodItemPrice);
        foodQuantity.setUrl(foodItemUrl);
        storeSharedPreferences.addFavorite(this, foodQuantity);
        finish();}
    }
}
