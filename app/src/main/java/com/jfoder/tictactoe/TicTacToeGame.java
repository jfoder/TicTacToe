package com.jfoder.tictactoe;

import android.widget.TextView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;

public class TicTacToeGame{
    private final int GAME_SIZE;
    private final int MAX_MOVES; //maximum number of moves available on specified gameSize
    private int moves;
    private AppCompatActivity mainActivity; //activity which give access to resources
    private ArrayList<Field> winningButtons; //list including IDs of buttons creating winning line
    private Field[][] fields; //array including Button objects and field states
    private GameState round;
    private TextView whoseRound;
    private TextView roundSymbol;
    private Player firstPlayer;
    private Player secondPlayer;


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
        public boolean checkIfCorrectMove(PlayerSymbol playerSymbol){
            if((this == CIRCLE_MOVE && playerSymbol == PlayerSymbol.CIRCLE)
                || (this == CROSS_MOVE && playerSymbol == PlayerSymbol.CROSS)){
                return true;
            }
            return false;
        }
        public GameState getOppositeTurn(){
            if(this == CIRCLE_MOVE)
                return CROSS_MOVE;
            else if(this == CROSS_MOVE)
                return CIRCLE_MOVE;
            else
                throw new IllegalArgumentException("Cannot return opposite turn for state: " + this.toString());
        }
    }

    public TicTacToeGame(int gameSize, Field[][] fields, Player firstPlayer, Player secondPlayer, AppCompatActivity mainActivity){
        this.GAME_SIZE = gameSize;
        this.mainActivity = mainActivity;
        this.whoseRound = mainActivity.findViewById(R.id.playerRound);
        this.roundSymbol = mainActivity.findViewById(R.id.roundSymbol);
        this.round = GameState.CIRCLE_MOVE;
        moves = 0;
        MAX_MOVES = gameSize * gameSize;
        winningButtons = new ArrayList<>();
        this.fields = fields;
        for(int i = 0; i < gameSize; i++){
            for(int j = 0; j < gameSize; j++){
                this.fields[i][j].setFieldState(FieldState.EMPTY);
                this.fields[i][j].setPosition(new Position(i, j));
            }
        }
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        firstPlayer.setPlayerSymbol(PlayerSymbol.CIRCLE);
        secondPlayer.setPlayerSymbol(PlayerSymbol.CROSS);
        firstPlayer.setGame(this);
        secondPlayer.setGame(this);
        updateGameState();
    }

    public void playerMove(Field field, Player player){
        if(field.getFieldState() != FieldState.EMPTY)
            throw new IllegalArgumentException("Invoked method playerMove for not empty field");
        if(!this.round.checkIfCorrectMove(player.getPlayerSymbol()))
            throw new IllegalArgumentException("Cannot make move during enemy's turn");
        this.round = this.round.getOppositeTurn();
        field.setFieldState(player.getPlayerSymbol().toFieldState());
        moves++;
        updateGameState();
        if(round == GameState.CIRCLE_MOVE || round == GameState.CROSS_MOVE) {
            if (player == firstPlayer) secondPlayer.informAboutRound();
            else firstPlayer.informAboutRound();
        }
    }

    public Field[][] getFields(){
        return this.fields;
    }

    public int getGameSize(){
        return this.GAME_SIZE;
    }

    public Player getCurrentPlayer(){
        if(round == GameState.CIRCLE_MOVE && firstPlayer.getPlayerSymbol() == PlayerSymbol.CIRCLE) return firstPlayer;
        else if(round == GameState.CROSS_MOVE && firstPlayer.getPlayerSymbol() == PlayerSymbol.CROSS) return  firstPlayer;
        return secondPlayer;
    }

    public void resetGame() {
        for(int i = 0; i < GAME_SIZE; i++){
            for(int j = 0; j < GAME_SIZE; j++){
                fields[i][j].setFieldState(FieldState.EMPTY);
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
                fields[i][j].getButton().setText(fields[i][j].getFieldState().getSymbol());
                fields[i][j].getButton().setBackgroundTintList(mainActivity.getResources().getColorStateList(R.color.color_grey));

            }
        }
        for(Field buttonState : winningButtons){
            buttonState.getButton().setBackgroundTintList(mainActivity.getResources().getColorStateList(R.color.color_light_yellow));
        }
    }

    private void updateGameState(){
        loop:
        for(int i = 0; i < GAME_SIZE; i++) {
            for(int j = 0; j < GAME_SIZE; j++) {
                if(checkIfInsideLine(i, j)){
                    if(fields[i][j].getFieldState() == FieldState.CIRCLE) round = GameState.CIRCLE_WIN;
                    else round = GameState.CROSS_WIN;
                    break loop;
                }
            }
        }
        if(round != GameState.CIRCLE_WIN && round != GameState.CROSS_WIN && moves == MAX_MOVES) round = GameState.DRAW;
        refreshGame();
    }

    private boolean checkIfInsideLine(int i, int j) { //method checks if field with coordinates i and j begins a winning line
        if(GAME_SIZE == 3 || GAME_SIZE == 4) {
            if (fields[i][j].getFieldState() != FieldState.EMPTY){
                if(j + 2 < GAME_SIZE) {
                    if(checkIfEqual(fields[i][j], fields[i][j+1], fields[i][j+2])) return true;
                }
                if(i + 2 < GAME_SIZE) {
                    if(checkIfEqual(fields[i][j], fields[i+1][j], fields[i+2][j])) return true;
                }
                if(i + 2 < GAME_SIZE && j + 2 < GAME_SIZE) {
                    if(checkIfEqual(fields[i][j], fields[i+1][j+1], fields[i+2][j+2])) return true;
                }
                if(i + 2 < GAME_SIZE && j - 2 >= 0) {
                    if(checkIfEqual(fields[i][j], fields[i+1][j-1], fields[i+2][j-2])) return true;
                }
                return false;
            }
            return false;
        }
        else if(GAME_SIZE == 5){
            if (fields[i][j].getFieldState() != FieldState.EMPTY){
                if(j + 3 < GAME_SIZE &&
                    checkIfEqual(fields[i][j], fields[i][j+1], fields[i][j+2], fields[i][j+3])) return true;
                if(i + 3 < GAME_SIZE &&
                    checkIfEqual(fields[i][j], fields[i+1][j], fields[i+2][j], fields[i+3][j])) return true;
                if(i + 3 < GAME_SIZE && j + 3 < GAME_SIZE &&
                    checkIfEqual(fields[i][j], fields[i+1][j+1], fields[i+2][j+2], fields[i+3][j+3])) return true;
                if(i + 3 < GAME_SIZE && j - 3 >= 0 &&
                    checkIfEqual(fields[i][j], fields[i+1][j-1], fields[i+2][j-2], fields[i+3][j-3])) return true;
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean checkIfEqual(Field... fields) {
        winningButtons.add(fields[0]);
        for(int i = 0; i < fields.length - 1; i++){
            winningButtons.add(fields[i + 1]);
            if(fields[i].getFieldState() != fields[i+1].getFieldState()) {
                winningButtons.clear();
                return false;
            }
        }
        return true;
    }
}