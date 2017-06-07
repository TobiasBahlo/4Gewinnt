package com.bahlot.a4gewinnt.Backend;

/**
 * Created by Toby on 30.05.2017.
 */

public class Board {

    private Field [][] board = new Field[6][7];

    public Board(){
        createBoard();
    }

    public void createBoard(){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                board[i][j] = new Field();
            }
        }
    }

    public Field[][] getBoard(){
        return board;
    }

    @Override
    public String toString(){
        String output = "";

        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                output += board[i][j] + "|";
            }
            output += "\n";
        }
        return output;
    }

}
