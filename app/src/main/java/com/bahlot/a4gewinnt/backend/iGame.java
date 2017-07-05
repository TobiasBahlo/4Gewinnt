package com.bahlot.a4gewinnt.backend;

/**
 * Created by Toby on 30.05.2017.
 */

public interface iGame {
    //standard game-methods
    public void startGame(String name1, eColor color1, String name2, eColor color2);
    public void saveGame(String filename);
    public void loadGame(String filename);
    public void finishGame();

    //getter
    public String getCurentBoard(); //the Board as String
    public String getCurentPlayer(); // toString of Player
    public String getCurentPlayerColorZug();
    public String getCurentPlayerColor();
    public String getCurentPlayerWinColor();
    public int[] getMove();
    public int getPositionOfPlayer(String name);
    public String getPlayerNameByPosition(int position);
    public String getCurentPlayerWinName();
    public String getCurentPlayerName();
    //other game-methods
    public void setCoin(int row);
    public boolean winGame();


    //wird wieder gelöscht, nur zum testen
    public boolean checkColumn(int column);


}