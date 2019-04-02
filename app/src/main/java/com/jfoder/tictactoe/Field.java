package com.jfoder.tictactoe;

import android.widget.Button;

public class Field {
    private Button button;
    private Position position;
    private FieldState fieldState;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public Field(Button button){
        this.button = button;
    }
}
