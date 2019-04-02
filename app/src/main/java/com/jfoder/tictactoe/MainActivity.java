package com.jfoder.tictactoe;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private int currentContentView;
    //0: activity_main
    //1: game3x3
    //2: 4x4 game
    //3  5x5 game
    private TicTacToeGame game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentContentView = 0;
    }

    public void onFieldButtonClick(View v){
        Position pos = ButtonProvider.getButtonPosition(v, game);
        if(pos != null){
            Player currentPlayer = game.getCurrentPlayer();
            if(currentPlayer.isHuman())
                game.getCurrentPlayer().makeMove(game.getFields()[pos.getX()][pos.getY()]);
        }
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.backButton) onBackPressed();
        else if(v.getId() == R.id.resetButton){
            if(game != null) game.resetGame();
        }
        else{
            Player humanPlayer = new HumanPlayer();
            Player computerPlayer = new ComputerPlayer();
            if(v.getId() == R.id.button3x3) {
                setContentView(R.layout.game3x3);
                currentContentView = 1;
                Field[][] fields = new Field[3][3];
                ButtonProvider.assignButtonsToFields(fields, 3, this);
                game = new TicTacToeGame(3, fields, humanPlayer, computerPlayer, this);
            }
            else if(v.getId() == R.id.button4x4) {
                setContentView(R.layout.game4x4);
                currentContentView = 2;
                Field[][] fields = new Field[4][4];
                ButtonProvider.assignButtonsToFields(fields, 4, this);
                game = new TicTacToeGame(4, fields, humanPlayer, computerPlayer, this);
            }
            else if(v.getId() == R.id.button5x5) {
                setContentView(R.layout.game5x5);
                currentContentView = 3;
                Field[][] fields = new Field[5][5];
                ButtonProvider.assignButtonsToFields(fields, 5, this);
                game = new TicTacToeGame(5, fields, humanPlayer, computerPlayer, this);
            }
        }
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
