package com.jfoder.tictactoe;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;

public class TTTGame{
    private final int GAME_SIZE;
    private final int MAX_MOVES; //maximum number of moves available on specified gameSize
    private int moves;
    private AppCompatActivity mainActivity; //activity which give access to resources
    private ArrayList<ButtonState> winningButtons; //list including IDs of buttons creating winning line
    private ButtonState[][] buttonsState; //array including Button objects and field states
    private GameState round;
    private TextView whoseRound;
    private TextView roundSymbol;


    private enum GameState{
        CIRCLE_MOVE(R.string.turnOfPlayer, "O"),
        CROSS_MOVE(R.string.turnOfPlayer, "X"),
        CIRCLE_WIN(R.string.theWinnerIs, "O"),
        CROSS_WIN(R.string.theWinnerIs, "X"),
        DRAW(R.string.draw, "");

        private int labelTextId;
        private String symbolText;
        GameState(int labelTextId, String symbolText){
            this.labelTextId = labelTextId;
            this.symbolText = symbolText;
        }
        public int getLabelTextId() { return labelTextId; }
        public String getSymbolText() { return symbolText; }
    }

    private enum FieldState{
        EMPTY(""),
        CIRCLE("O"),
        CROSS("X");

        private String symbol;
        FieldState(String symbol){
            this.symbol = symbol;
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

    public TTTGame(int gameSize, Button[][] buttons, AppCompatActivity mainActivity){
        this.GAME_SIZE = gameSize;
        this.mainActivity = mainActivity;
        this.whoseRound = mainActivity.findViewById(R.id.playerRound);
        this.roundSymbol = mainActivity.findViewById(R.id.roundSymbol);
        this.round = GameState.CIRCLE_MOVE;
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
        for(int i = 0; i < GAME_SIZE; i++){
            for(int j = 0; j < GAME_SIZE; j++){
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
        for(int i = 0; i < GAME_SIZE; i++){
            for(int j = 0; j < GAME_SIZE; j++){
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
        whoseRound.setText(mainActivity.getString(round.getLabelTextId()));
        roundSymbol.setText(round.getSymbolText());
        for(int i = 0; i < GAME_SIZE; i++){
            for(int j = 0; j < GAME_SIZE; j++){
                buttonsState[i][j].getButton().setText(buttonsState[i][j].fieldState.getSymbol());
                buttonsState[i][j].getButton().setBackgroundTintList(mainActivity.getResources().getColorStateList(R.color.color_grey));

            }
        }
        for(ButtonState buttonState : winningButtons){
            buttonState.getButton().setBackgroundTintList(mainActivity.getResources().getColorStateList(R.color.color_light_yellow));
        }
    }

    private void updateGameState(){
        loop:
        for(int i = 0; i < GAME_SIZE; i++) {
            for(int j = 0; j < GAME_SIZE; j++) {
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
        if(GAME_SIZE == 3 || GAME_SIZE == 4) {
            if (buttonsState[i][j].fieldState != FieldState.EMPTY){
                if(j + 2 < GAME_SIZE) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i][j+1], buttonsState[i][j+2])) return true;
                }
                if(i + 2 < GAME_SIZE) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j], buttonsState[i+2][j])) return true;
                }
                if(i + 2 < GAME_SIZE && j + 2 < GAME_SIZE) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j+1], buttonsState[i+2][j+2])) return true;
                }
                if(i + 2 < GAME_SIZE && j - 2 >= 0) {
                    if(checkIfEqual(buttonsState[i][j], buttonsState[i+1][j-1], buttonsState[i+2][j-2])) return true;
                }
                return false;
            }
            return false;
        }
        else if(GAME_SIZE == 5){
            if (buttonsState[i][j].fieldState != FieldState.EMPTY){
                if(j + 3 < GAME_SIZE &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i][j+1], buttonsState[i][j+2], buttonsState[i][j+3])) return true;
                if(i + 3 < GAME_SIZE &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i+1][j], buttonsState[i+2][j], buttonsState[i+3][j])) return true;
                if(i + 3 < GAME_SIZE && j + 3 < GAME_SIZE &&
                    checkIfEqual(buttonsState[i][j], buttonsState[i+1][j+1], buttonsState[i+2][j+2], buttonsState[i+3][j+3])) return true;
                if(i + 3 < GAME_SIZE && j - 3 >= 0 &&
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