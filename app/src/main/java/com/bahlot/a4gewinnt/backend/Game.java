package com.bahlot.a4gewinnt.backend;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Created by Toby on 30.05.2017.
 */

public class Game implements iGame{

    private Player[] players = new Player[2];
    private Player curentPlayer;
    private Board board;
    private int[] move = new int[2];
    private String colorA;
    public Game(){
        board = new Board();
    }

    public void addPlayer(String name, eColor color) {
        Player p = null;
        try{
            p = new Player(name, color);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        if(players[0] == null){ //if playerlist is empty, add new player at index 0
            players[0] = p;
        }else{					//else the new player is at index 1
            players[1] = p;
        }
    }


    public void changePlayer(){
        if(curentPlayer == players[0]){
            curentPlayer = players[1];
        }else{
            curentPlayer = players[0];
        }
    }

    public String getPlayerNameByPosition(int pos){
        pos = Math.max(pos, 0);
        pos = Math.min(pos, 1);

        return players[pos].getName();
    }

    public void startGame(String name1, eColor color1, String name2, eColor color2) {
        addPlayer(name1, color1);
        addPlayer(name2, color2);
        curentPlayer = players[0];
    }

    public void saveGame(String filename) {
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileWriter(filename + ".csv"));
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
        pw.write(this.toString());
        if(pw != null){ pw.close(); }
    }

    public void loadGame(String filename){
        BufferedReader br = null;
        String tmp = "";
        String read;
        try{
            //file is uploaded
            br = new BufferedReader(new FileReader(filename + ".csv"));
            read = br.readLine();

            while(read != null){
                tmp += read + "\n";
                read = br.readLine();
            }

            //after loading the file, the file will be used -> splitted
            String [] parts = tmp.split("\n");
            String []cPlayer = parts[0].split(";");
            String []player1 = parts[1].split(";");
            String [] player2 = parts[2].split(";");
            String boardL = "";
            for(int i=3; i < parts.length; i++){
                boardL += parts[i]+ "\n";
            }

            //set curentplayer
            if(cPlayer[1].equals("red")){
                curentPlayer = new Player(cPlayer[0], eColor.red);
            } else if(cPlayer[1].equals("blue")){
                curentPlayer = new Player(cPlayer[0], eColor.yellow);
            }
            //set player 1
            if(player1 [1].equals("red")){
                players[0] = new Player(player1 [0], eColor.red);
            } else if(player1 [1].equals("blue")){
                players[0] = new Player(player1 [0], eColor.yellow);
            }

            //set player 2
            if(player2 [1].equals("red")){
                players[1] = new Player(player2 [0], eColor.red);
            } else if(player1 [1].equals("blue")){
                players[1] = new Player(player2 [0], eColor.yellow);
            }
            //set board
            board.boardLoad(boardL);

        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }

        if(br != null){
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void finishGame() {
        System.exit(0);

    }

    public Board getBoardObject() {
        return board;
    }

    public String getCurentBoard(){
        return board.toString();
    }


    public String getCurentPlayer(){
        return curentPlayer.toString();
    }
    public String getCurentPlayerName(){
        return curentPlayer.getName().toString();
    }
    public String getCurentPlayerWinName(){
        if (players[0].getName().equals(curentPlayer.getName().toString())) {
            return players[1].getName();
        }
        return players[0].getName();

        }
    public String getCurentPlayerColorZug(){return colorA;}
    public String getCurentPlayerColor(){return curentPlayer.getColor().toString();}
    public String getCurentPlayerWinColor(){
        if (players[0].getName().equals(curentPlayer.getColor().toString())) {
            return players[1].getColor().toString();
        }
        return players[0].getColor().toString();

    }
    public int getPositionOfPlayer(String name){

        if(players[0].getName().equals(name)){
        return 0;
        }else{
            return 1;
        }

    }
    public int[] getMove(){return move;}
    public void setCoin(int column) {
        if(column < 0 || column > 6){
            throw new RuntimeException("Invalid column number");
        }
        for(int i=board.getBoard().length-1; i >= 0; i--){
            if(board.getBoard()[i][column].getColor() == eColor.none){
                board.getBoard()[i][column].setColor(curentPlayer.getColor());
                move[0]=i;
                Log.d("I","I : "+i);
                move[1]=column;
                Log.d("Column","Column : "+column);
                colorA = curentPlayer.getColor().toString();
                break;
            }
        }
        changePlayer();
    }

    //check if the game was won
    public boolean winGame(){
        boolean hasWon = false;
        //check if won by columns
        for(int i=0;i<board.getBoard()[0].length;i++){
            hasWon = checkColumn(i);
            if(hasWon){
                return hasWon;
            }
        }

        //check if won by columns
        for(int i=0;i<board.getBoard().length;i++){
            hasWon = checkRow(i);
            if(hasWon){
                return hasWon;
            }
        }

        //check if won by diagonal left to right
        hasWon = this.checkDiagonalL2R();
        if(hasWon){ return hasWon;};

        //check if won by diagonal right to lefft
        hasWon = this.checkDiagonalR2L();
        if(hasWon){ return hasWon;};

        return hasWon;
    }

    //method to check if there are 4 coins with the same color in a column
    public boolean checkColumn(int column){
        boolean hasWon = false;
        //check if there are exact 4 coins in a column -> condition to win
        if(board.getBoard()[2][column].getColor() != eColor.none && board.getBoard()[1][column].getColor() == eColor.none){
            //check if the color of the 4 coins are the same
            if(board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor() &&
                    board.getBoard()[3][column].getColor() == board.getBoard()[4][column].getColor() &&
                    board.getBoard()[4][column].getColor() == board.getBoard()[5][column].getColor()){

                hasWon = true;
            }
        }
        //check if there are exact 5 coins in a column
        else if(board.getBoard()[1][column].getColor() != eColor.none && board.getBoard()[0][column].getColor() == eColor.none){
            ////check if index 2-5 is same or if index 1 - 4 is same
            if(board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor() &&
                    board.getBoard()[3][column].getColor() == board.getBoard()[4][column].getColor() &&
                    board.getBoard()[4][column].getColor() == board.getBoard()[5][column].getColor() ||

                    board.getBoard()[1][column].getColor() == board.getBoard()[2][column].getColor() &&
                            board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor() &&
                            board.getBoard()[3][column].getColor() == board.getBoard()[4][column].getColor()){

                hasWon = true;
            }
        }
        //check if there are exact 6 coins in a column
        else if(board.getBoard()[0][column].getColor() != eColor.none){
            ////check if index 2-5 is same or if index 1 - 4 is same or if index 0 -3 is same
            if(board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor() &&
                    board.getBoard()[3][column].getColor() == board.getBoard()[4][column].getColor() &&
                    board.getBoard()[4][column].getColor() == board.getBoard()[5][column].getColor() ||

                    board.getBoard()[1][column].getColor() == board.getBoard()[2][column].getColor() &&
                            board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor() &&
                            board.getBoard()[3][column].getColor() == board.getBoard()[4][column].getColor() ||

                    board.getBoard()[0][column].getColor() == board.getBoard()[1][column].getColor() &&
                            board.getBoard()[1][column].getColor() == board.getBoard()[2][column].getColor() &&
                            board.getBoard()[2][column].getColor() == board.getBoard()[3][column].getColor()){

                hasWon = true;
            }
        }
        return hasWon;
    }

    //method to check if there are 4 coins with the same color in a row
    public boolean checkRow(int row){
        boolean hasWon = false;
        //check if there are 4 coins in a row from index 0-3
        if(board.getBoard()[row][0].getColor() != eColor.none && board.getBoard()[row][1].getColor() != eColor.none &&
                board.getBoard()[row][2].getColor() != eColor.none && board.getBoard()[row][3].getColor() != eColor.none){
            //check if the color of the 4 coins are the same
            if(board.getBoard()[row][0].getColor() == board.getBoard()[row][1].getColor() &&
                    board.getBoard()[row][1].getColor() == board.getBoard()[row][2].getColor() &&
                    board.getBoard()[row][2].getColor() == board.getBoard()[row][3].getColor()){

                hasWon = true;
            }
        }
        //check if there are 4 coins in a row from index 1-4
        if(board.getBoard()[row][1].getColor() != eColor.none && board.getBoard()[row][2].getColor() != eColor.none &&
                board.getBoard()[row][3].getColor() != eColor.none && board.getBoard()[row][4].getColor() != eColor.none){
            //check if the color of the 4 coins are the same
            if(board.getBoard()[row][1].getColor() == board.getBoard()[row][2].getColor() &&
                    board.getBoard()[row][2].getColor() == board.getBoard()[row][3].getColor() &&
                    board.getBoard()[row][3].getColor() == board.getBoard()[row][4].getColor()){

                hasWon = true;
            }
        }
        //check if there are 4 coins in a row from index 2-5
        if(board.getBoard()[row][2].getColor() != eColor.none && board.getBoard()[row][3].getColor() != eColor.none &&
                board.getBoard()[row][4].getColor() != eColor.none && board.getBoard()[row][5].getColor() != eColor.none){
            //check if the color of the 4 coins are the same
            if(board.getBoard()[row][2].getColor() == board.getBoard()[row][3].getColor() &&
                    board.getBoard()[row][3].getColor() == board.getBoard()[row][4].getColor() &&
                    board.getBoard()[row][4].getColor() == board.getBoard()[row][5].getColor()){

                hasWon = true;
            }
        }
        //check if there are 4 coins in a row from index 3-6
        if(board.getBoard()[row][3].getColor() != eColor.none && board.getBoard()[row][4].getColor() != eColor.none &&
                board.getBoard()[row][5].getColor() != eColor.none && board.getBoard()[row][6].getColor() != eColor.none){
            //check if the color of the 4 coins are the same
            if(board.getBoard()[row][3].getColor() == board.getBoard()[row][4].getColor() &&
                    board.getBoard()[row][4].getColor() == board.getBoard()[row][5].getColor() &&
                    board.getBoard()[row][5].getColor() == board.getBoard()[row][6].getColor()){

                hasWon = true;
            }
        }
        return hasWon;
    }

    //method to check if there are 4 coins diagonal with the same color from left to right
    public boolean checkDiagonalL2R(){
        boolean hasWon = false;
        for(int i=0; i<board.getBoard()[0].length; i++){ //every column is checked for itself
            if(i >= 0 && i <= 3){ // if index is bigger than 3, there will be an out of bound exception
                for(int j=board.getBoard().length-1; j>2;j--){//starts at the last row, counts down until j is 3, if j is smaller then 3 -> out of bound exception
                    if(board.getBoard()[j][i].getColor() != eColor.none && board.getBoard()[j-1][i+1].getColor() != eColor.none
                            && board.getBoard()[j-2][i+2].getColor() != eColor.none && board.getBoard()[j-3][i+3].getColor() != eColor.none){

                        if(board.getBoard()[j][i].getColor() == board.getBoard()[j-1][i+1].getColor() &&
                                board.getBoard()[j-1][i+1].getColor() == board.getBoard()[j-2][i+2].getColor() &&
                                board.getBoard()[j-2][i+2].getColor() == board.getBoard()[j-3][i+3].getColor()){
                            hasWon = true;
                        }
                    }
                }
            }

        }
        return hasWon;
    }

    //method to check if there are 4 coins diagonal with the same color from right to left
    public boolean checkDiagonalR2L(){
        boolean hasWon = false;
        for(int i=board.getBoard()[0].length-1; i >= 0; i--){ //every column is checked for itself
            if(i >= 3 && i <= 6){ // if index is smaller than , there will be an out of bound exception
                for(int j=board.getBoard().length-1; j>2;j--){//starts at the last row, counts down until j is 3, if j is smaller then 3 -> out of bound exception
                    if(board.getBoard()[j][i].getColor() != eColor.none && board.getBoard()[j-1][i-1].getColor() != eColor.none
                            && board.getBoard()[j-2][i-2].getColor() != eColor.none && board.getBoard()[j-3][i-3].getColor() != eColor.none){

                        if(board.getBoard()[j][i].getColor() == board.getBoard()[j-1][i-1].getColor() &&
                                board.getBoard()[j-1][i-1].getColor() == board.getBoard()[j-2][i-2].getColor() &&
                                board.getBoard()[j-2][i-2].getColor() == board.getBoard()[j-3][i-3].getColor()){
                            hasWon = true;
                        }
                    }
                }
            }

        }
        return hasWon;
    }


    @Override
    public String toString(){
        String output = "";
        output += curentPlayer + "\n";
        output += players[0] + "\n";
        output += players[1] + "\n";
        output += board + "\n";
        return output;
    }

}
