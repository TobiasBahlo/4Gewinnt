package com.bahlot.a4gewinnt.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

import com.bahlot.a4gewinnt.backend.eColor;

/**
 * Created by Martin Voehringer on 6/11/17.
 */

/**
 * Base class for network interaction
 */
@SuppressWarnings("WeakerAccess")
class NetClient extends Thread{

    /** Handler aquired by the using class */
    private Handler usingClassHandler;
    /** SocketListener listening for messages from the network */
    private SocketListener listener;
    /** The socket used to connect to the server */
    private Socket socket;
    /** Adress of the server to connect to */
    private String serverAdress;
    /** Port number on which the server is listening */
    private int portNumber;
    /** Writer used to write to the socket */
    private BufferedWriter socketWriter;

    private NetToClientHandler netToClientHandler;

    /**
     * This handler handles messages to this thread so we can
     * write to the socket from it (otherwise everything will be called on the main thread)
     */
    private static class NetToClientHandler extends Handler{
        private WeakReference<NetClient> netClientWeakReference;

        public NetToClientHandler(NetClient client){
            this.netClientWeakReference = new WeakReference<NetClient>(client);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                this.netClientWeakReference.get().socketWriter.write(msg.obj.toString() + "\r\n");
                this.netClientWeakReference.get().socketWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public NetClient(Handler usingClassHandler, String serverAdress, int portNumber){
        if (usingClassHandler == null){
            throw new IllegalArgumentException("handler can't be null, need a way to communicate");
        }

        if (serverAdress == null || serverAdress.isEmpty()){
            serverAdress = "127.0.0.1";
        }

        if (portNumber < 0 || portNumber > 65535){
            portNumber = 8080;
        }

        this.usingClassHandler = usingClassHandler;
        this.serverAdress = serverAdress;
        this.portNumber = portNumber;


    }

    @Override
    protected void finalize() throws Throwable {
        if (this.isConnected()){
            this.disconnectFromServer();
        }
        super.finalize();
    }

    private void disconnectFromServer() {
        if (this.isConnected()){
            try {
                this.socket.close();
            } catch (IOException e) {
                // TODO: Something useful here please
                e.printStackTrace();
            }
        }
    }

    public void newGame(String playerOneName, eColor playerOneColor){
        try {
            JSONObject obj = MessageBuilder.createNewGameMessage(playerOneName, playerOneColor);
            this.sendMessageOnSocket(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            this.sendMessageToUsingHandler(eMessageType.CREATE_GAME_FAILURE, e.getMessage());
        }
    }

    public void joinGame(String gameName, String playerTwoName, eColor playerTwoColor){
        try {
            JSONObject obj = MessageBuilder.joinGameMessage(gameName, playerTwoName, playerTwoColor);
            this.sendMessageOnSocket(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            this.sendMessageToUsingHandler(eMessageType.JOIN_GAME_FAILURE, e.getMessage());
        }
    }

    public void setCoin(int column){
        if (this.isConnected()){
            try {
                JSONObject obj = MessageBuilder.setCoinMessage(column);
                this.sendMessageOnSocket(obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                this.sendMessageToUsingHandler(eMessageType.SET_COIN_FAILURE, e.getMessage());
            }


        } else {
            throw new IllegalStateException("Tried to setCoin but is not connected to server!");
        }
    }

    public boolean isConnected() {
        return this.socket != null;
    }


    @Override
    public void run() {
        if (!this.isConnected()){
            try {
                Looper.prepare();
                this.netToClientHandler = new NetToClientHandler(this);
                // Connect
                this.socket = new Socket(this.serverAdress, this.portNumber);
                // Setup writing
                this.socketWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                // Create a listener thread
                this.listener = new SocketListener(this.socket, this.usingClassHandler);
                this.listener.listen();

                sendMessageToUsingHandler(eMessageType.CONNECTION_SUCCESS, null);
                Looper.loop();

            } catch (IOException e) {
                e.printStackTrace();
                this.sendMessageToUsingHandler(eMessageType.CONNECTION_FAILURE, e.getMessage());
            }
        }
    }

    private void sendMessageToUsingHandler(int type, Object message){
        Message msg = this.usingClassHandler.obtainMessage(type, message);
        msg.sendToTarget();
    }

    private void sendMessageOnSocket(String message){
        Message m = this.netToClientHandler.obtainMessage(0, message);
        m.sendToTarget();
    }

}
