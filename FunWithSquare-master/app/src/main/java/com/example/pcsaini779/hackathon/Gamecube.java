package com.example.pcsaini779.hackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class Gamecube extends AppCompatActivity {
    TextView txtView, CorrectNum,Score, Round;
    Boolean flag1 = false, flag2 = false;
    Button btnNext, btnSolve, OK;
    TextView editText;
    Random r;
    String Square="3";
    int randomNumber, size = 0, a, start = 3, control = 0, TotalScore = 0,CurrentScore=0,x = 0, k = 0;
    ArrayList<Integer> y;
    PatriciaTrieTest ptt = new PatriciaTrieTest();
    PatriciaTrie pt = new PatriciaTrie();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        r = new Random();
        y = new ArrayList<Integer>();
        txtView = (TextView) findViewById(R.id.txtView);
        Round = (TextView) findViewById(R.id.round);
        Score = (TextView) findViewById(R.id.score);
        CorrectNum = (TextView) findViewById(R.id.corrNum);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSolve = (Button) findViewById(R.id.btnSolve);
        editText = (TextView) findViewById(R.id.pickNumber);
        OK = (Button) findViewById(R.id.btnSubmit);
        btnNext.setEnabled(flag2);
        y = ptt.game(pt, start, Square);
        randomNumber=pickNumber();
        a = randomNumber / 10;

        txtView.setText("" + a);
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CorrectNum.setText((int) Math.cbrt(randomNumber) + " : " + randomNumber);
                flag2 = true;
                flag1 = false;
                btnNext.setEnabled(flag2);
                OK.setEnabled(flag1);

            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();

                if (s != null && s != "") {
                    flag2 = true;
                    btnNext.setEnabled(flag2);
                    String s1 = randomNumber + "";
                    String s2 = a + "";
                    String s3 = s2 + s;

                    if (s1.equals(s3)) {
                        txtView.setText("" + s3);
                        CurrentScore += 5;
                        Score.setText("Score: " + CurrentScore);
                    } else {
                        txtView.setText("" + s3);
                        if (CurrentScore > 2) {
                            CurrentScore -= 2;
                        }

                    }
                    CorrectNum.setText((int) Math.cbrt(randomNumber) + " : " + randomNumber);
                    editText.setText("");
                    Score.setText("Score: " + CurrentScore);
                    flag1 = false;
                    OK.setEnabled(flag1);
                    flag2 = true;
                    btnNext.setEnabled(flag2);
                } else {
                    flag2 = false;
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag2 == true) {
                    if (control < 3) {
                        control++;
                        flag2 = false;
                        randomNumber=pickNumber();
                        a = randomNumber / 10;
                        txtView.setText("" + a);
                        CorrectNum.setText("");


                    } else {
                        if (CurrentScore > 15) {
                            Round.setText("Round " + (start-1));
                            pt.makeEmpty();
                            start++;
                            y = ptt.game(pt, start, Square);
                            randomNumber=pickNumber();//
                            a = randomNumber / 10;
                            txtView.setText("" + a);
                            control = 0;
                            TotalScore+=CurrentScore;
                            Log.e("Hello","Total Score"+TotalScore);
                            CurrentScore = 0;

                            CorrectNum.setText("");
                        } else {
                            pt.makeEmpty();
                            y = ptt.game(pt, start, Square);
                            randomNumber=pickNumber();
                            a = randomNumber / 10;
                            txtView.setText("" + a);
                            control = 0;
                            CurrentScore = 0;
                        }
                    }
                    OK.setEnabled(true);
                    flag1 = true;
                    flag2 = false;
                    btnNext.setEnabled(flag2);
                }

            }
        });
    }
    public  int pickNumber(){
     Toast.makeText(this,"hello"+y.size(),Toast.LENGTH_SHORT).show();
        x = r.nextInt(y.size());//
        randomNumber = y.get(x);
        y.remove(x);
        return randomNumber;
    }

    public void putNumber(View v){
        editText.setText(v.getTag().toString());
    }

}
