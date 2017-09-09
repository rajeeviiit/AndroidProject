package com.example.pcsaini779.hackathon;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnPlay,btnInst,btnTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnInst = (Button) findViewById(R.id.btnInst);
        btnTeam = (Button) findViewById(R.id.btnTeam);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ChooseGame.class));
            }
        });
        btnInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Instructions.class));
            }
        });
        btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Team.class));
            }
        });

        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setColorFilter(Color.parseColor("#ffffff"));

    }
}
