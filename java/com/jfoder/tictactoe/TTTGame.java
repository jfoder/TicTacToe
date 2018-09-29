package com.jfoder.tictactoe;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TTTGame{
    private final int gameSize;
    private int moves;
    private final int MAX_MOVES; //maximum number of moves available on specified gameSize
    private ButtonState[][] buttonsState;
    private GameState round;
    private TextView whoseRound;
    private TextView roundSymbol;

    private enum GameState{
        CIRCLE_MOVE,
        CROSS_MOVE,
        CIRCLE_WIN,
        CROSS_WIN,
        DRAW
    }

    private enum FieldState{
        EMPTY,
        CIRCLE,
        CROSS
    }

    private class ButtonState{
        private FieldState fieldState;
        private Button button;
        public ButtonState(Button b) {
            fieldState = FieldState.EMPTY;
            button = b;
        }

        public FieldState getButtonState() { return fieldState; }
        public Button getButton() { return button; }
        public void setButtonState(FieldState state) { fieldState = state; }
    }

    public TTTGame(int gameSize, Button[][] buttons, TextView whoseRound, TextView roundSymbol){
        this.gameSize = gameSize;
        this.whoseRound = whoseRound;
        this.roundSymbol = roundSymbol;
        this.round = GameState.CIRCLE_MOVE;
        moves = 0;
        MAX_MOVES = gameSize * gameSize;
        buttonsState = new ButtonState[gameSize][gameSize];
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                Log.d("DEBUG", ""+ buttons[0][0].getId());
                buttonsState[i][j] = new ButtonState(buttons[i][j]);
            }
        }
        setOnClickListeners();
        updateGameState();
    }

    private void setOnClickListeners() {
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                Button b = buttonsState[i][j].getButton();
                final ButtonState btn = buttonsState[i][j];
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(round == GameState.CIRCLE_MOVE && btn.fieldState == FieldState.EMPTY){
                            ((Button)v).setText("O");
                            btn.fieldState = FieldState.CIRCLE;
                            round = GameState.CROSS_MOVE;
                            moves++;
                            updateGameState();
                        }
                        else if(round == GameState.CROSS_MOVE && btn.fieldState == FieldState.EMPTY){
                            ((Button)v).setText("X");
                            btn.fieldState = FieldState.CROSS;
                            round = GameState.CIRCLE_MOVE;
                            moves++;
                            updateGameState();
                        }
                    }
                });
            }
        }
    }

    private void updateGameState(){
        for(int i = 0; i < gameSize; i++) {
            for(int j = 0; j < gameSize; j++) {
                if(checkIfInsideLine(i, j)){
                    if(buttonsState[i][j].fieldState == FieldState.CIRCLE) round = GameState.CIRCLE_WIN;
                    else round = GameState.CROSS_WIN;
                    break;
                }
            }
        }
        if(round == GameState.CIRCLE_MOVE && moves < MAX_MOVES) roundSymbol.setText("O");
        else if(round == GameState.CROSS_MOVE && moves < MAX_MOVES) roundSymbol.setText("X");
        else if(round == GameState.CIRCLE_WIN){
            whoseRound.setText("Wygrał gracz:");
            roundSymbol.setText("O");
        }
        else if(round == GameState.CROSS_WIN){
            whoseRound.setText("Wygrał gracz:");
            roundSymbol.setText("X");
        }
        else if(moves == MAX_MOVES) {
            round = GameState.DRAW;
            whoseRound.setText("Remis!");
            roundSymbol.setText("");
        }

    }

    private boolean checkIfInsideLine(int i, int j) { //method checks if field with coordinates i and j is beginning of winnig line
        Log.d("DEBUG", "Checking method called");
        if(gameSize == 3 || gameSize == 4) {
            if (buttonsState[i][j].fieldState != FieldState.EMPTY){
                if(j + 2 < gameSize) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i][j+1], buttonsState[i][j+2])) return true;
                }
                if(i + 2 < gameSize) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j], buttonsState[i+2][j])) return true;
                }
                if(i + 2 < gameSize && j + 2 < gameSize) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j+1], buttonsState[i+2][j+2])) return true;
                }
                return false;
            }
            return false;
        }
        else if(gameSize == 5){
            if (buttonsState[i][j].fieldState != FieldState.EMPTY){
                if(j + 3 < gameSize &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i][j+1], buttonsState[i][j+2], buttonsState[i][j+3])) return true;
                if(i + 3 < gameSize &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i+1][j], buttonsState[i+2][j], buttonsState[i+3][j])) return true;
                if(i + 3 < gameSize && j + 3 < gameSize &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i+1][j+1], buttonsState[i+2][j+2], buttonsState[i+3][j+3])) return true;
                if(i + 3 < gameSize && j - 3 >= 0 &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i+1][j-1], buttonsState[i+2][j-2], buttonsState[i+3][j-3])) return true;
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean checkIfEqual(ButtonState... buttons) {
        for(int i = 0; i < buttons.length - 1; i++){
            if(buttons[i].fieldState != buttons[i+1].fieldState) return false;
        }
        return true;
    }
}
