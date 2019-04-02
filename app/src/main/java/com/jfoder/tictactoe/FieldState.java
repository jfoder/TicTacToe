package com.jfoder.tictactoe;

public enum FieldState{
    EMPTY(""),
    CIRCLE("O"),
    CROSS("X");

    private String symbol;
    FieldState(String symbol){
        this.symbol = symbol;
    }
    public String getSymbol() { return symbol; }
}