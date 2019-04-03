package com.jfoder.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private enum CurrentContent{
        MAIN_MENU,
        GAME
    }
    private CurrentContent currentContent;
    private TicTacToeGame game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentContent = CurrentContent.MAIN_MENU;
    }

    public void onFieldButtonClick(View v){
        Position pos = ButtonProvider.getButtonPosition(v, game);
        if(pos != null){
            Player currentPlayer = game.getCurrentPlayer();
            if(currentPlayer.isHuman())
                game.getCurrentPlayer().makeMove(game.getFields()[pos.getX()][pos.getY()]);
        }
    }

    public void onNavigateButtonClick(View v){
        if(v.getId() == R.id.backButton) onBackPressed();
        else if(v.getId() == R.id.resetButton){
            if(game != null) game.resetGame();
        }
    }

    public void onStartButtonClick(View v){
        Player humanPlayer = new HumanPlayer();
        Player computerPlayer = new ComputerPlayer();
        currentContent = CurrentContent.GAME;
        int gameSize = 3;
        if(v.getId() == R.id.button3x3) {
            setContentView(R.layout.game3x3);
            gameSize = 3;
        }
        else if(v.getId() == R.id.button4x4) {
            setContentView(R.layout.game4x4);
            gameSize = 4;
        }
        else if(v.getId() == R.id.button5x5) {
            setContentView(R.layout.game5x5);
            gameSize = 5;
        }
        Field[][] fields = new Field[gameSize][gameSize];
        ButtonProvider.assignButtonsToFields(fields, gameSize, this);
        game = new TicTacToeGame(gameSize, fields, humanPlayer, computerPlayer, this);
    }

    @Override
    public void onBackPressed() {
        if(currentContent != CurrentContent.MAIN_MENU) {
            setContentView(R.layout.activity_main);
            currentContent = CurrentContent.MAIN_MENU;
        }
        else{
            System.exit(0);
        }
    }
}
