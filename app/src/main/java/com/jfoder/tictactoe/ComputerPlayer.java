package com.jfoder.tictactoe;


import java.util.Random;

public class ComputerPlayer extends Player {
    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public void informAboutRound() {
        Field[][] fields = game.getFields();
        while(true){
            Random rand = new Random();
            int x = rand.nextInt(game.getGameSize());
            int y = rand.nextInt(game.getGameSize());
            if(fields[x][y].getFieldState() == FieldState.EMPTY){
                makeMove(fields[x][y]);
                return;
            }
        }
    }
}
