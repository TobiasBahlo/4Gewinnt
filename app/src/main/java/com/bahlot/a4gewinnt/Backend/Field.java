package com.bahlot.a4gewinnt.Backend;

/**
 * Created by Toby on 30.05.2017.
 */

public class Field {
    private eColor color;

    public Field(){
        setColor(eColor.none);
    }

    public void setColor(eColor color) {
        if(color == null) throw new RuntimeException("Color is invalid");
        this.color = color;
    }

    public eColor getColor(){
        return color;
    }

    @Override
    public String toString(){
        String output = "";
        if(color == eColor.none){
            output = "o";
        }else if(color == eColor.red){
            output = "r";
        }else if(color == eColor.yellow){
            output = "y";
        }
        return output;
    }
}
