package com.jfoder.tictactoe;

public enum PlayerSymbol {
    CIRCLE,
    CROSS;

    public FieldState toFieldState(){
        if(this == CIRCLE) return FieldState.CIRCLE;
        else return FieldState.CROSS;
    }
}
