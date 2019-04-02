package com.jfoder.tictactoe;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ButtonProvider {

    public static void assignButtonsToFields(Field[][] fields, int gameSize, AppCompatActivity activity){
        String seeked;
        Resources res = activity.getResources();
        for(int i = 0; i < gameSize; i++) {
            for(int j = 0; j < gameSize; j++){
                seeked = "button" + gameSize + "_" + i + j;
                int buttonId = res.getIdentifier(seeked, "id", activity.getApplicationContext().getPackageName());
                fields[i][j] = new Field((Button)activity.findViewById(buttonId));
            }
        }
    }

    public static Position getButtonPosition(View v, TicTacToeGame game){
        if(game == null) return null;
        String resName = v.getResources().getResourceName(v.getId());
        if(!(resName.contains("button3_") || resName.contains("button4_") || resName.contains("button5_"))) return null;
        int length = resName.length();
        int x = Integer.parseInt("" + resName.charAt(length - 2));
        int y = Integer.parseInt("" + resName.charAt(length - 1));
        if(x >= 0 && y >= 0) return new Position(x, y);
        else return null;
    }
}
