package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView homeSmallIcon, menuSmallIcon, orderSmallIcon, accountSmallIcon, settingsSmallIcon;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();


    LinearLayout home, menuHere, order, user, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        home = (LinearLayout) findViewById(R.id.homeSmallIconrl);
        menuHere = (LinearLayout) findViewById(R.id.menuSmallIconrl);
        user = (LinearLayout) findViewById(R.id.accountSmallIconrl);
        order = (LinearLayout) findViewById(R.id.orderSmallIconrl);
        settings = (LinearLayout) findViewById(R.id.settingsSmallIconrl);

        menuSmallIcon = (ImageView)findViewById(R.id.menuSmallIcon);
        homeSmallIcon = (ImageView)findViewById(R.id.homeSmallIcon);
        orderSmallIcon = (ImageView)findViewById(R.id.orderSmallIcon);
        accountSmallIcon = (ImageView)findViewById(R.id.accountSmallIcon);
        settingsSmallIcon = (ImageView)findViewById(R.id.settingsSmallIcon);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setState(getApplicationContext(), "0");
                Intent intent = new Intent(getApplicationContext(), MenuMain.class);
                startActivity(intent);
                finish();

            }
        });
        menuHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setState(getApplicationContext(), "3");
                Toast.makeText(MenuPage.this, "home",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuMain.class);
                startActivity(intent);
                finish();

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setState(getApplicationContext(), "2");
                if(storeSharedPreferences.loadFavorites(getApplicationContext())!=null) {
                    Toast.makeText(getApplicationContext(), "order", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ProceedOrder.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "No Items Selected", Toast.LENGTH_LONG).show();
                }

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setState(getApplicationContext(), "4");
                Intent intent = new Intent(getApplicationContext(), MenuMain.class);
                startActivity(intent);
                finish();

            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.setOffscreenPageLimit(5);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


                        DrawableCompat.setTint(orderSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(menuSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                DrawableCompat.setTint(homeSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(accountSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(settingsSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Veg(), "VEG");
        adapter.addFragment(new NonVeg(), "NON-VEG");
        adapter.addFragment(new Bev(), "Beverages");
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
