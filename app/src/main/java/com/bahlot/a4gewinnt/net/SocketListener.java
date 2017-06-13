package com.bahlot.a4gewinnt.net;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


/**
 * Created by Martin Voehringer on 6/11/17.
 */

class SocketListener extends Thread{
    private Socket socket;
    private Handler handler;
    private BufferedReader socketReader;

    public SocketListener(Socket socket, Handler handler) throws IOException {
        if (socket == null){
            throw new IllegalArgumentException("Socket was null");
        }

        if (handler == null){
            throw new IllegalArgumentException("Handler was null");
        }

        this.socket = socket;
        this.handler = handler;
        this.socketReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    void listen() {
        this.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                String line = this.socketReader.readLine();

                if (line != null && !line.isEmpty()){
                    Message msg = Message.obtain(this.handler, eMessageType.JSON_DATA, line);
                    msg.sendToTarget();
                }
            } catch (IOException e) {
                // TODO: Useful stuff
                e.printStackTrace();
            }
        }
    }
}