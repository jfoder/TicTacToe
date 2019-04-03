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
        Field moveToWin, moveToBlock;
        if(playerSymbol == PlayerSymbol.CIRCLE){
            moveToWin = findBestField(fields, FieldState.CIRCLE);
            moveToBlock = findBestField(fields, FieldState.CROSS);
        }
        else{
            moveToWin = findBestField(fields, FieldState.CROSS);
            moveToBlock = findBestField(fields, FieldState.CIRCLE);
        }
        if(moveToWin != null){
            makeMove(moveToWin);
            return;
        }
        if(moveToBlock != null){
            makeMove(moveToBlock);
            return;
        }
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

    private Field findBestField(Field[][] fields, FieldState fieldState){
        for(int i = 0; i < game.getGameSize(); i++){
            for(int j = 0; j < game.getGameSize(); j++){
                if(checkIfGoodMove(fields, i, j, fieldState))
                    return fields[i][j];
            }
        }
        return null;
    }

    private boolean checkIfGoodMove(Field[][] fields, int i, int j, FieldState fieldState){
        if(fields[i][j].getFieldState() != FieldState.EMPTY) return false;
        if(i > 0 && i < game.getGameSize() - 1){
            if(fields[i - 1][j].getFieldState() == fields[i + 1][j].getFieldState() && fields[i - 1][j].getFieldState() == fieldState)
                return true;
        }
        if(j > 0 && j < game.getGameSize() - 1){
            if(fields[i][j - 1].getFieldState() == fields[i][j + 1].getFieldState() && fields[i][j - 1].getFieldState() == fieldState)
                return true;
        }
        if(i > 0 && i < game.getGameSize() - 1 && j > 0 && j < game.getGameSize() - 1){
            if(fields[i - 1][j - 1].getFieldState() == fields[i + 1][j + 1].getFieldState() && fields[i - 1][j - 1].getFieldState() == fieldState)
                return true;
            if(fields[i - 1][j + 1].getFieldState() == fields[i + 1][j - 1].getFieldState() && fields[i + 1][j - 1].getFieldState() == fieldState)
                return true;
        }
        if(i < game.getGameSize() - 2){
            if(fields[i + 1][j].getFieldState() == fields[i + 2][j].getFieldState() && fields[i + 1][j].getFieldState() == fieldState)
                return true;
        }
        if(j < game.getGameSize() - 2){
            if(fields[i][j + 1].getFieldState() == fields[i][j + 2].getFieldState() && fields[i][j + 1].getFieldState() == fieldState)
                return true;
        }
        if(i > 1){
            if(fields[i - 1][j].getFieldState() == fields[i - 2][j].getFieldState() && fields[i - 1][j].getFieldState() == fieldState)
                return true;
        }
        if(j > 1){
            if(fields[i][j - 1].getFieldState() == fields[i][j - 2].getFieldState() && fields[i][j - 1].getFieldState() == fieldState)
                return true;
        }
        if(i < game.getGameSize() - 2 && j < game.getGameSize() - 2){
            if(fields[i + 1][j + 1].getFieldState() == fields[i + 2][j + 2].getFieldState() && fields[i + 1][j + 1].getFieldState() == fieldState)
                return true;
        }
        if(i > 1 && j > 1){
            if(fields[i - 1][j - 1].getFieldState() == fields[i - 2][j - 2].getFieldState() && fields[i - 1][j - 1].getFieldState() == fieldState)
                return true;
        }
        if(i < game.getGameSize() - 2 && j > 1){
            if(fields[i + 1][j - 1].getFieldState() == fields[i + 2][j - 2].getFieldState() && fields[i + 1][j - 1].getFieldState() == fieldState)
                return true;
        }
        if(i > 1 && j < game.getGameSize() - 2){
            if(fields[i - 1][j + 1].getFieldState() == fields[i - 2][j + 2].getFieldState() && fields[i - 1][j + 1].getFieldState() == fieldState)
                return true;
        }
        return false;
    }
}
