package in.aviaryan.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView txtUserScore, txtComputerScore, txtLogs;
    ScrollView scrollLogs;
    ImageView imgDice;
    Button btnRoll, btnHold, btnReset;
    private Random random = new Random();
    private Random otherRandom = new Random();
    private int currentUserScore, currentComputerScore, sureComputerScore;
    private final int MAX_SCORE = 100;
    private int diceIcons [] = {
            R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    };
    private Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(in.aviaryan.scarnesdice.R.layout.activity_main);
        // instantiate views
        txtUserScore = (TextView) findViewById(R.id.userScore);
        txtComputerScore = (TextView) findViewById(R.id.computerScore);
        imgDice = (ImageView) findViewById(R.id.diceImage);
        btnRoll = (Button) findViewById(R.id.btnRoll);
        btnHold = (Button) findViewById(R.id.btnHold);
        btnReset = (Button) findViewById(R.id.btnReset);
        txtLogs = (TextView) findViewById(R.id.txtLogs);
        scrollLogs = (ScrollView) findViewById(R.id.scrollLogs);
        // start game
        // onStart()
        // event listeners
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLog("User gave a HOLD. Computer turn now");
                startComputerTurn();
            }
        });
    }

    Runnable computerTurnRunnable = new Runnable() {
        @Override
        public void run() {
            if (otherRandom.nextInt(10) < 7 || currentComputerScore == 0){ // 70% chances to play
                // play first chance anyway, don't hold first chance
                boolean b = computerTurn();
                if (b){
                    timerHandler.postDelayed(this, 1000);
                } else {
                    endComputerTurn();
                }
            } else { // hold
                addLog("Computer gave a HOLD. User turn now");
                endComputerTurn();
            }
        }
    };

    private void endComputerTurn(){
        updateComputerScoreBoard();
        btnHold.setEnabled(true);
        btnRoll.setEnabled(true);
    }

    private void startComputerTurn(){
        btnHold.setEnabled(false);
        btnRoll.setEnabled(false);
        currentUserScore = 0;
        currentComputerScore = 0;
        sureComputerScore = Integer.parseInt(txtComputerScore.getText().toString());
        timerHandler.postDelayed(computerTurnRunnable, 500);
    }

    private void updateComputerScoreBoard(){
        txtComputerScore.setText("" + (sureComputerScore + currentComputerScore));
    }

    private boolean computerTurn(){
        int num;
        num = random.nextInt(6) + 1;
        imgDice.setImageResource(diceIcons[num-1]);
        if (num == 1){
            currentComputerScore = 0;
            addLog("Computer rolled a 1. RESET");
            return false;
        } else {
            addLog("Computer rolled a " + num);
            currentComputerScore += num;
            updateComputerScoreBoard();
            if ((sureComputerScore + currentComputerScore) >= MAX_SCORE){
                endGame("Computer");
                return false;
            }
        }
        return true;
    }

    private void rollDice(){
        int num = random.nextInt(6) + 1;
        int currentScore = Integer.parseInt(txtUserScore.getText().toString());
        imgDice.setImageResource(diceIcons[num-1]);
        if (num == 1){
            currentScore -= currentUserScore;
            txtUserScore.setText(currentScore + "");
            addLog("User rolled a 1. RESET");
            startComputerTurn();
        } else {
            currentScore += num;
            if (currentScore >= MAX_SCORE){
                endGame("User");
                return;
            }
            currentUserScore += num;
            addLog("User rolled a " + num);
            txtUserScore.setText(currentScore + "");
        }
    }

    private void endGame(String winner){
        (Toast.makeText(this, "Game over. " + winner + " won", Toast.LENGTH_LONG)).show();
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUserScore = 0;
        currentComputerScore = 0;
        sureComputerScore = 0;
        txtUserScore.setText("0");
        txtComputerScore.setText("0");
        txtLogs.setText("");
        timerHandler.removeCallbacks(computerTurnRunnable);
    }

    private void addLog(String msg){
        txtLogs.append("\n" + msg);
        scrollToBottom();
    }

    private void scrollToBottom() {
        // http://stackoverflow.com/a/8532016/2295672
        scrollLogs.post(new Runnable() {
            public void run() {
                scrollLogs.smoothScrollTo(0, txtLogs.getBottom());
            }
        });
    }
}
