package com.bahlot.a4gewinnt.net;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

class eMessageType {
    /** Indicates that the client successfully connected to the server */
    final static int CONNECTION_SUCCESS = 0;
    /** Indicates that the connection could not be established, see obj field for description */
    final static int CONNECTION_FAILURE = 1;
    /** Indicates that this message contains JSON string data in its obj field */
    final static int JSON_DATA = 2;
    /** Indicates that this client has failed to even send the request to create a new game */
    final static int CREATE_GAME_FAILURE = 3;
    /** Indicates that this client failed to send a request to join a game */
    final static int JOIN_GAME_FAILURE = 4;
    /** Indicates that this client failed to send a request to set a coin */
    final static int SET_COIN_FAILURE = 5;

}
