package com.bahlot.a4gewinnt.Backend;

/**
 * Created by Toby on 30.05.2017.
 */

public interface iGame {
    //standard game-methods
    public void startGame();
    public void saveGame();
    public void loadGame();
    public void finishGame();

    //getter
    public String getCurentBoard(); //the Board as String
    public String getCurentPlayer(); // toString of Player

    //other game-methods
    public void setCoin(int row);
    public boolean winGame();


    //wird wieder gelöscht, nur zum testen
    public boolean checkColumn(int column);


}