package com.bahlot.a4gewinnt.net;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

import com.bahlot.a4gewinnt.backend.eColor;

/**
 * Interface used to communicate events from the net
 */
public abstract class NetGameListener {

    /**
     * Called when the net client successfully connected to the server and is ready to
     * send and receive data
     */
    public void onConnectionEstablished(){

    }

    /**
     * Called when the net client failed to connect to the server
     */
    public void onConnectionFailure(String reason){

    }

    /**
     * Called after a game has registered on the server and is waiting for a
     * second player to join
     * @param gameName The name the server uses to identify the game
     */
    public void onGameCreated(String gameName){

    }

    /**
     * Called when creation of a game has failed for some reason
     * @param reason A description of the reason of failure
     */
    public void onCreateGameFailed(String reason){

    }

    /**
     * Called when a second player has joined the game on the server
     * @param secondPlayerName Name of the second player that joined
     * @param secondPlayerColor Color of the second player that joined
     */
    public void onSecondPlayerJoined(String secondPlayerName, eColor secondPlayerColor){

    }

    /**
     * Called when this player has successfully joined a game
     * @param gameName Name of the game on the server
     * @param firstPlayerName Name of the first player
     * @param firstPlayerColor Color of the first player
     */
    public void onJoinedGame(String gameName, String firstPlayerName, eColor firstPlayerColor){

    }

    /**
     * Called when joining an existing game failed for some reason
     * @param reason The reason of failure
     */
    public void onJoinGameFailed(String reason){

    }

    /**
     * Called when the other player wants to set a coin
     * @param column Column of the coin
     * @param playerName Name of the player that wants to set the column
     */
    public void onSecondPlayerSetCoin(int column, String playerName){

    }

    /**
     * Called when the request to set the coin was successfull
     */
    public void onCoinSet(){

    }

    /**
     * Called when set coin has failed
     * @param reason The reason of failure
     */
    public void onSetCoinFailed(String reason){

    }

    /**
     * Called when the other player of a game disconnects
     */
    public void onSecondPlayerDisconnect(){

    }
}
