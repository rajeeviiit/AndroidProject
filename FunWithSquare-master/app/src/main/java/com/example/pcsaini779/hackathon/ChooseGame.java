package com.example.pcsaini779.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseGame extends AppCompatActivity {

    private Button cube,sqaure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

     cube =(Button)findViewById(R.id.cube);
     sqaure=(Button)findViewById(R.id.square) ;


    cube.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ChooseGame.this,Gamecube.class));

        }
    });


     sqaure.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(ChooseGame.this,Game.class));


         }
     });



    }
}
