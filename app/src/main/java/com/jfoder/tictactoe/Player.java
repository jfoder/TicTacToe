package com.jfoder.tictactoe;

public abstract class Player {
    protected TicTacToeGame game;
    protected PlayerSymbol playerSymbol;

    public void makeMove(Field field){
        if(field.getFieldState() == FieldState.EMPTY)
            game.playerMove(field, this);
    }

    public abstract boolean isHuman();

    public void setGame(TicTacToeGame game){
        this.game = game;
    }

    public void setPlayerSymbol(PlayerSymbol playerSymbol){
        this.playerSymbol = playerSymbol;
    }

    public PlayerSymbol getPlayerSymbol(){
        return this.playerSymbol;
    }

    public abstract void informAboutRound();
}
