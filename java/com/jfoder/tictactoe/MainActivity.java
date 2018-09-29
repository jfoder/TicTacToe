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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentContentView = 0;
    }

    public void onButtonClick(View v) {
        Log.d("DEBUG", "onButtonClick called");
        if(v.getId() == R.id.button3x3) {
            setContentView(R.layout.game3x3);
            currentContentView = 1;
            Log.d("DEBUG", "3x3 button clicked");
            Button[][] buttons = getButtonsArray(3);
            TextView t1 = (TextView) findViewById(R.id.playerRound);
            TextView t2 = (TextView) findViewById(R.id.roundSymbol);
            TTTGame game3x3 = new TTTGame(3, buttons, t1, t2);
        }
        if(v.getId() == R.id.button4x4) {
            setContentView(R.layout.game4x4);
            currentContentView = 2;
            Log.d("DEBUG", "4x4 button clicked");
            Button[][] buttons = getButtonsArray(4);
            TextView t1 = (TextView) findViewById(R.id.playerRound);
            TextView t2 = (TextView) findViewById(R.id.roundSymbol);
            TTTGame game3x3 = new TTTGame(4, buttons, t1, t2);
        }
        if(v.getId() == R.id.button5x5) {
            setContentView(R.layout.game5x5);
            currentContentView = 3;
            Log.d("DEBUG", "5x5 button clicked");
            Button[][] buttons = getButtonsArray(5);
            TextView t1 = (TextView) findViewById(R.id.playerRound);
            TextView t2 = (TextView) findViewById(R.id.roundSymbol);
            TTTGame game3x3 = new TTTGame(5, buttons, t1, t2);
        }
    }

    private Button[][] getButtonsArray(int gameSize) {
        Button[][] result = new Button[gameSize][gameSize];
        String seeked;
        Resources res = getResources();
        for(int i = 0; i < gameSize; i++) {
            for(int j = 0; j < gameSize; j++){
                seeked = "button" + gameSize + "_" + i + j;
                Log.d("DEBUG", seeked);
                int buttonId = res.getIdentifier(seeked, "id", getApplicationContext().getPackageName());
                result[i][j] = (Button) findViewById(buttonId);
                Log.d("DEBUG", "ID: " + result[i][j].getId());
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        Log.d("DEBUG", "Back button pressed");
        if(currentContentView != 0) {
            setContentView(R.layout.activity_main);
            currentContentView = 0;
        }
        else{
            System.exit(0);
        }
    }
}
