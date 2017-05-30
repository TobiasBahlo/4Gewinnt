package com.bahlot.a4gewinnt.Backend;

/**
 * Created by Toby on 30.05.2017.
 */

public class Player {
    private String name;
    private eColor color;

    public Player(String name, eColor color){
        setName(name);
        setColor(color);
    }

    public void setColor(eColor color) {
        if(color == null) throw new RuntimeException("Color is invalid");
        this.color = color;
    }

    public void setName(String name) {
        if(name == null || name.length() < 2 || name.length() > 20) throw new RuntimeException("Name is invalid");
        this.name = name;
    }

    public eColor getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return name + ";" + color;
    }
}
