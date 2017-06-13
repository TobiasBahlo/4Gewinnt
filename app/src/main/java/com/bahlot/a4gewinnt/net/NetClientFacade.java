package com.bahlot.a4gewinnt.net;

import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import com.bahlot.a4gewinnt.backend.eColor;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

public class NetClientFacade {

    private static class SocketHandler extends Handler {
        private WeakReference<iNetGame> callbackRef;

        SocketHandler(iNetGame callbackRef) {
            this.callbackRef = new WeakReference<iNetGame>(callbackRef);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case eMessageType.CONNECTION_SUCCESS:{
                    this.callbackRef.get().onConnectionEstablished();
                } break;
                case eMessageType.CONNECTION_FAILURE:{
                    this.callbackRef.get().onConnectionFailure(msg.obj.toString());
                } break;
                case eMessageType.CREATE_GAME_FAILURE:{
                    this.callbackRef.get().onCreateGameFailed(msg.obj.toString());
                } break;
                case eMessageType.JOIN_GAME_FAILURE:{
                    this.callbackRef.get().onJoinGameFailed(msg.obj.toString());
                } break;
                case eMessageType.JSON_DATA:{
                    this.handleJSONData(msg.obj.toString());
                }
            }
        }

        private void handleJSONData(String jsonString) {
            JSONObject obj;
            try {
                obj = new JSONObject(jsonString);

                String actionType = obj.getString(JSONStrings.ACTION);

                // Check if this is a server response
                if (actionType.equals(JSONStrings.AT_SERVERRESPONSE)){
                    String responseTo = obj.getString(JSONStrings.RS_RESPTO);

                    // Response to create game
                    switch (responseTo) {
                        case JSONStrings.AT_CREATEGAME:
                            this.handleCreateGameResponse(obj);
                            break;
                        case JSONStrings.AT_JOINGAME:
                            this.handleJoinGameResponse(obj);
                            break;
                        case JSONStrings.AT_SETCOIN:
                            this.handleSetCoinResponse(obj);
                            break;
                    }
                // Check if this is a server event
                } else if (actionType.equals(JSONStrings.AT_SERVEREVENT)){
                    String eventType = obj.getString(JSONStrings.EVENT_TYPE);

                    switch (eventType){
                        case JSONStrings.EV_2NDPLAYERCOIN:
                            this.handle2ndPlayerCoin(obj);
                            break;
                        case JSONStrings.EV_2NDPLAYERJOINED:
                            this.handle2ndPlayerJoined(obj);
                            break;
                    }
                }

            } catch (JSONException e) {
                // TODO: Not much we can do here...
                e.printStackTrace();
                return;
            }

        }

        private void handle2ndPlayerJoined(JSONObject obj) throws JSONException {
            String secondPlayerName = obj.getString(JSONStrings.P2NAME);
            eColor secondPlayerColor = eColString.convertToECol(obj.getString(JSONStrings.P2COLOR));
            this.callbackRef.get().onSecondPlayerJoined(secondPlayerName, secondPlayerColor);
        }

        private void handle2ndPlayerCoin(JSONObject obj) throws JSONException {
            String playerName = obj.getString(JSONStrings.P2NAME);
            int column = obj.getInt(JSONStrings.COLUMN);

            this.callbackRef.get().onSecondPlayerSetCoin(column, playerName);
        }

        private void handleSetCoinResponse(JSONObject obj) throws JSONException {
            if (this.wasSuccess(obj)){
                this.callbackRef.get().onCoinSet();
            } else {
                String reason = this.getServerMessage(obj);
                this.callbackRef.get().onSetCoinFailed(reason);
            }
        }

        private void handleJoinGameResponse(JSONObject obj) throws JSONException {
            if (this.wasSuccess(obj)){
                String gameName = obj.getString(JSONStrings.GAMENAME);
                String p1Name = obj.getString(JSONStrings.P1NAME);
                eColor p1Color = eColString.convertToECol(obj.getString(JSONStrings.P1COLOR));
                this.callbackRef.get().onJoinedGame(gameName, p1Name, p1Color);
            } else {
                String reason = this.getServerMessage(obj);
                this.callbackRef.get().onJoinGameFailed(reason);
            }
        }

        private void handleCreateGameResponse(JSONObject obj) throws JSONException {
            if (this.wasSuccess(obj)){
                String gameName = obj.getString(JSONStrings.GAMENAME);
                this.callbackRef.get().onGameCreated(gameName);
            } else {
                String reason = this.getServerMessage(obj);
                this.callbackRef.get().onCreateGameFailed(reason);
            }
        }

        private boolean  wasSuccess(JSONObject obj) throws JSONException {
            boolean error = obj.getBoolean(JSONStrings.RS_STATUS);

            return error;
        }

        private String getServerMessage(JSONObject obj) throws JSONException {
            String message = obj.getString(JSONStrings.RS_MESSAGE);
            return message;
        }
    }

    private NetClient client;
    private iNetGame callbackInterface;

    public NetClientFacade(iNetGame callbackInterface){
        if (callbackInterface == null){
            throw new IllegalArgumentException("Callbackinterface must not be null");
        }

        this.callbackInterface = callbackInterface;
    }

    public void connectToServer(String adress, int port){
        this.client = new NetClient(new SocketHandler(this.callbackInterface), adress, port);
        this.client.start();
    }

    public void newGame(String playerOneName, eColor playerOneColor) {
        this.client.newGame(playerOneName, playerOneColor);
    }

    public void joinGame(String gameName, String playerTwoName, eColor playerTwoColor){
        this.client.joinGame(gameName, playerTwoName, playerTwoColor);
    }

    public void setCoin(int column){
        this.client.setCoin(column);
    }

}
