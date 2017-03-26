package com.example.prateek.bimsapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProceedOrder extends AppCompatActivity {

    private ViewPager pager;

    TextView cartNavigator, locationNavigator, remarksNavigator, summaryNavigator;
    LinearLayout  nextButton, backButton;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ProceedOrder.MyOrderPagerAdapter(getSupportFragmentManager()));

        Toast.makeText(this, storeSharedPreferences.getUserEmail(this), Toast.LENGTH_SHORT).show();

        pager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
        pager.setOffscreenPageLimit(5);

        cartNavigator = (TextView)findViewById(R.id.cartNavigator);
        locationNavigator = (TextView)findViewById(R.id.addressNavigator);
        nextButton = (LinearLayout) findViewById(R.id.nextButton);
        backButton =(LinearLayout) findViewById(R.id.backButton);
        remarksNavigator = (TextView)findViewById(R.id.remarksNavigator);
        summaryNavigator = (TextView)findViewById(R.id.summaryNavigator);

        cartNavigator.setTextColor(getResources().getColor(R.color.black));
        locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
        remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
        summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));

        backButton.setVisibility(View.INVISIBLE);

        if(pager.getCurrentItem()!=3){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backButton.setVisibility(View.VISIBLE);
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                    if(pager.getCurrentItem()==1){
                        locationNavigator.setTextColor(getResources().getColor(R.color.black));
                        cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    }else if(pager.getCurrentItem()==2){
                        remarksNavigator.setTextColor(getResources().getColor(R.color.black));
                        locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    }else if(pager.getCurrentItem()==3){
                        nextButton.setVisibility(View.INVISIBLE);
                        summaryNavigator.setTextColor(getResources().getColor(R.color.black));
                        cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                        locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));

                    }

                }
            });}
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
                if(pager.getCurrentItem()==1){
                    locationNavigator.setTextColor(getResources().getColor(R.color.black));
                    cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                }else if(pager.getCurrentItem()==2){
                    nextButton.setVisibility(View.VISIBLE);
                    remarksNavigator.setTextColor(getResources().getColor(R.color.black));
                    locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                }else if(pager.getCurrentItem()==3){
                    summaryNavigator.setTextColor(getResources().getColor(R.color.black));
                    cartNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                }else if(pager.getCurrentItem()==0){
                    backButton.setVisibility(View.INVISIBLE);
                    cartNavigator.setTextColor(getResources().getColor(R.color.black));
                    summaryNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    remarksNavigator.setTextColor(getResources().getColor(R.color.colorInactive));
                    locationNavigator.setTextColor(getResources().getColor(R.color.colorInactive));

                }
            }
        });//}

    }

    public void cancelProceedOrder(View view) {finish();
    }


    private class MyOrderPagerAdapter extends FragmentPagerAdapter {

        public MyOrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0:{
                    return OrderFragment.newInstance(0);}
                case 1: {
                    cartNavigator.setTextColor(getResources().getColor(R.color.black));
                    return LocationFragment.newInstance(0);
                }
                case 2: return RemarksFragment.newInstance(0);
                case 3: return SummaryFragment.newInstance(0);
                default: return HomeFragment.newInstance(0);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}

