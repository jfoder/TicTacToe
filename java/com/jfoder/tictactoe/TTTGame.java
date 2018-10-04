package com.jfoder.tictactoe;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;

public class TTTGame extends AppCompatActivity{
    private final int gameSize;
    private int moves;
    private final int MAX_MOVES; //maximum number of moves available on specified gameSize
    private ArrayList<ButtonState> winningButtons; //list include IDs of buttons creating winning line
    private ButtonState[][] buttonsState;
    private GameState round;
    private TextView whoseRound;
    private TextView roundSymbol;
    private Context context;

    private enum GameState{
        CIRCLE_MOVE,
        CROSS_MOVE,
        CIRCLE_WIN,
        CROSS_WIN,
        DRAW;

        private String labelText;
        private String symbolText;
        static{
            CIRCLE_MOVE.labelText = "Ruch gracza:";
            CROSS_MOVE.labelText = "Ruch gracza:";
            CIRCLE_WIN.labelText = "Wygrał gracz:";
            CROSS_WIN.labelText = "Wygrał gracz:";
            DRAW.labelText = "Remis!";

            CIRCLE_MOVE.symbolText = "O";
            CROSS_MOVE.symbolText = "X";
            CIRCLE_WIN.symbolText = "O";
            CROSS_WIN.symbolText = "X";
            DRAW.symbolText = "";

        }
        public String getLabelText() { return labelText; }
        public String getSymbolText() { return symbolText; }
    }

    private enum FieldState{
        EMPTY,
        CIRCLE,
        CROSS;

        private String symbol;
        static{
            EMPTY.symbol = "";
            CIRCLE.symbol = "O";
            CROSS.symbol = "X";
        }
        public String getSymbol() { return symbol; }
    }

    private class ButtonState{
        private FieldState fieldState;
        private Button button;
        public ButtonState(Button b) {
            fieldState = FieldState.EMPTY;
            button = b;
        }
        public Button getButton() { return button; }
    }

    public TTTGame(int gameSize, Button[][] buttons, TextView playerRound, TextView roundSymbol, Context context){
        this.gameSize = gameSize;
        this.whoseRound = playerRound;
        this.roundSymbol = roundSymbol;
        this.round = GameState.CIRCLE_MOVE;
        this.context = context;
        moves = 0;
        MAX_MOVES = gameSize * gameSize;
        winningButtons = new ArrayList<>();
        buttonsState = new ButtonState[gameSize][gameSize];
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
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

    public void resetGame() {
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                buttonsState[i][j].fieldState = FieldState.EMPTY;
                winningButtons.clear();
                moves = 0;
                round = GameState.CIRCLE_MOVE;
            }
        }
        winningButtons.clear();
        refreshGame();
    }

    private void refreshGame() {
        whoseRound.setText(round.getLabelText());
        roundSymbol.setText(round.getSymbolText());
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                buttonsState[i][j].getButton().setText(buttonsState[i][j].fieldState.getSymbol());
                buttonsState[i][j].getButton().setBackgroundTintList(context.getResources().getColorStateList(R.color.color_grey));

            }
        }
        for(ButtonState buttonState : winningButtons){
            buttonState.getButton().setBackgroundTintList(context.getResources().getColorStateList(R.color.color_light_yellow));
        }
    }

    private void updateGameState(){
        loop:
        for(int i = 0; i < gameSize; i++) {
            for(int j = 0; j < gameSize; j++) {
                if(checkIfInsideLine(i, j)){
                    if(buttonsState[i][j].fieldState == FieldState.CIRCLE) round = GameState.CIRCLE_WIN;
                    else round = GameState.CROSS_WIN;
                    break loop;
                }
            }
        }
        if(round != GameState.CIRCLE_WIN && round != GameState.CROSS_WIN && moves == MAX_MOVES) round = GameState.DRAW;
        refreshGame();
    }

    private boolean checkIfInsideLine(int i, int j) { //method checks if field with coordinates i and j is beginning of winning line
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
                if(i + 2 < gameSize && j - 2 >= 0) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j-1], buttonsState[i+2][j-2])) return true;
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
        winningButtons.add(buttons[0]);
        for(int i = 0; i < buttons.length - 1; i++){
            winningButtons.add(buttons[i + 1]);
            if(buttons[i].fieldState != buttons[i+1].fieldState) {
                winningButtons.clear();
                return false;
            }
        }
        return true;
    }
}
