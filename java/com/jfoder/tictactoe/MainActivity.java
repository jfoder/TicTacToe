package com.jfoder.tictactoe;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int currentContentView;
    //0: activity_main
    //1: game3x3
    //2: 4x4 game
    //3  5x5 game

    private TTTGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentContentView = 0;
    }

    public void onButtonClick(View v) {
        if(v.getId() == R.id.backButton) onBackPressed();
        else if(v.getId() == R.id.resetButton){
            if(game != null) game.resetGame();
        }
        else{
            if(v.getId() == R.id.button3x3) {
                setContentView(R.layout.game3x3);
                currentContentView = 1;
                TextView t1 = findViewById(R.id.playerRound);
                TextView t2 = findViewById(R.id.roundSymbol);
                Button[][] buttons = getButtonsArray(3);
                game = new TTTGame(3, buttons, t1, t2, getApplicationContext());
            }
            else if(v.getId() == R.id.button4x4) {
                setContentView(R.layout.game4x4);
                currentContentView = 2;
                TextView t1 = findViewById(R.id.playerRound);
                TextView t2 = findViewById(R.id.roundSymbol);
                Button[][] buttons = getButtonsArray(4);
                game = new TTTGame(4, buttons, t1, t2, getApplicationContext());
            }
            else if(v.getId() == R.id.button5x5) {
                setContentView(R.layout.game5x5);
                currentContentView = 3;
                TextView t1 = findViewById(R.id.playerRound);
                TextView t2 = findViewById(R.id.roundSymbol);
                Button[][] buttons = getButtonsArray(5);
                game = new TTTGame(5, buttons, t1, t2, getApplicationContext());
            }
        }
    }

    private Button[][] getButtonsArray(int gameSize) {
        Button[][] result = new Button[gameSize][gameSize];
        String seeked;
        Resources res = getResources();
        for(int i = 0; i < gameSize; i++) {
            for(int j = 0; j < gameSize; j++){
                seeked = "button" + gameSize + "_" + i + j;
                int buttonId = res.getIdentifier(seeked, "id", getApplicationContext().getPackageName());
                result[i][j] = (Button) findViewById(buttonId);
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if(currentContentView != 0) {
            setContentView(R.layout.activity_main);
            currentContentView = 0;
        }
        else{
            System.exit(0);
        }
    }
}
