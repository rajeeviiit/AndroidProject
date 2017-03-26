package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirstPage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this one is the real shit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Toast.makeText(this, "10", Toast.LENGTH_SHORT).show();

        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);

        storeSharedPreferences.removeAllQuant(this);

   //     if(isNetworkAvailable()){
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                   // if(StoreSharedPreferences.getUserEmail(getApplicationContext()).length()!=0) {
                        Intent i = new Intent(FirstPage.this, SelectRestraunt.class);
                        startActivity(i);
                    //}
//                    else{
//                        Intent i = new Intent(FirstPage.this, Login.class);
//                        startActivity(i);
//                    }

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);

        //getAllMenu();


//        }
//        else{
//            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
//        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
