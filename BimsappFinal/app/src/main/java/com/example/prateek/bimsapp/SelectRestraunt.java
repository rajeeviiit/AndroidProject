package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SelectRestraunt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_restraunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void b1(View view) {
        this.setTheme(R.style.green);
        Intent i = new Intent(SelectRestraunt.this, MenuMain.class);
        startActivity(i);
    }

    public void b2(View view) {
        this.setTheme(R.style.blue);
        Intent i = new Intent(SelectRestraunt.this, MenuMain.class);
        startActivity(i);
    }

    public void b3(View view) {
    }

    public void goToRestraunt(View view) {
        Intent i = new Intent(SelectRestraunt.this, MenuMain.class);
        startActivity(i);
    }
}
