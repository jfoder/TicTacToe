package com.jfoder.tictactoe;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("Both x and y values cannot be negative: " + x + ", " + y);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
