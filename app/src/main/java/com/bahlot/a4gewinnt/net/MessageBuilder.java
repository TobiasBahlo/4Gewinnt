package com.bahlot.a4gewinnt.net;


import org.json.JSONException;
import org.json.JSONObject;

import com.bahlot.a4gewinnt.backend.eColor;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

class MessageBuilder {

    static JSONObject createNewGameMessage(String playerOneName, eColor playerOneColor) throws JSONException {
        JSONObject obj = null;

        if (playerOneName != null && !playerOneName.isEmpty() && playerOneColor != null) {
            obj = initObject(JSONStrings.AT_CREATEGAME);
            obj.put(JSONStrings.P1NAME, playerOneName);
            obj.put(JSONStrings.P1COLOR, eColString.convertFromECol(playerOneColor));
        }

        return obj;
    }

    static JSONObject joinGameMessage(String gameName, String playerTwoName, eColor playerTwoColor) throws JSONException {
        JSONObject obj = null;

        if (gameName != null && !gameName.isEmpty() &&
                playerTwoName != null && !playerTwoName.isEmpty() &&
                playerTwoColor != null){
            obj = initObject(JSONStrings.AT_JOINGAME);
            obj.put(JSONStrings.GAMENAME, gameName);
            obj.put(JSONStrings.P2NAME, playerTwoName);
            obj.put(JSONStrings.P2COLOR, eColString.convertFromECol(playerTwoColor));
        }

        return obj;
    }

    static JSONObject setCoinMessage(int column) throws JSONException {
        JSONObject obj = null;

        if (column >= 0){
            obj = initObject(JSONStrings.AT_SETCOIN);
            obj.put(JSONStrings.COLUMN, column);
        }

        return obj;
    }

    private static JSONObject initObject(String actionType) throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put(JSONStrings.ACTION, actionType);

        return obj;
    }
}
