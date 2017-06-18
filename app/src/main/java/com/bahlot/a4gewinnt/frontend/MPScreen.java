package com.bahlot.a4gewinnt.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bahlot.a4gewinnt.backend.eColor;
import com.bahlot.a4gewinnt.net.NetClientFacade;
import com.bahlot.a4gewinnt.net.NetGameListener;
import com.bahlot.a4gewinnt.net.eColString;


/**
 * Created by ihatenames on 6/18/17.
 */

public class MPScreen extends AppCompatActivity {

    private class NetListener extends NetGameListener{
        private Activity hostActivity;

        public NetListener(Activity hostActivity){
            if (hostActivity == null){
                throw new IllegalArgumentException("hostActivity was null!");
            }
            this.hostActivity = hostActivity;
        }

        @Override
        public void onConnectionEstablished() {
            toggleStatus(false, "Connected!");
            Toast.makeText(this.hostActivity, "Connection established!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectionFailure(String reason) {
            toggleStatus(false, reason);
            Toast.makeText(this.hostActivity, "Connection failed! " + reason, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onGameCreated(String gameName) {
            toggleStatus(true, "Game name: " + gameName + ": Waiting for second player to join...");
            Toast.makeText(this.hostActivity, "Game (name: " + gameName +") created, waiting for second player...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreateGameFailed(String reason) {
            p1Name = null;
            p1Color = null;
            toggleStatus(false, reason);
            Toast.makeText(this.hostActivity, "Game creation failed! " + reason, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onJoinedGame(String gameName, String firstPlayerName, eColor firstPlayerColor) {
            startGame(firstPlayerName, eColString.convertFromECol(firstPlayerColor),
                    p2Name, p2Color,
                    p2Name);
        }

        @Override
        public void onSecondPlayerJoined(String secondPlayerName, eColor secondPlayerColor) {
            startGame(p1Name, p1Color,
                    secondPlayerName, eColString.convertFromECol(secondPlayerColor),
                    p1Name);
        }

        @Override
        public void onJoinGameFailed(String reason) {
            p2Name = null;
            p2Color = null;
            toggleStatus(false, reason);
            Toast.makeText(this.hostActivity, "Failed to join game! " + reason, Toast.LENGTH_LONG).show();
        }
    }

    ProgressBar progressBar;
    TextView statusText;
    NetListener netListener;

    EditText newGameName;
    EditText joinGameName;
    EditText joinGamePlayerName;

    RadioGroup colorRadioGroup;
    RadioGroup colorRadioGroupJoin;

    String p1Name;
    String p1Color;

    String p2Name;
    String p2Color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mp_screen);

        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        this.statusText = (TextView) this.findViewById(R.id.statusText);
        this.newGameName = (EditText) this.findViewById(R.id.newGameP1Name);
        this.colorRadioGroup = (RadioGroup) this.findViewById(R.id.radioGroupColor);
        this.joinGameName = (EditText) this.findViewById(R.id.joinGameName);
        this.joinGamePlayerName = (EditText) this.findViewById(R.id.joinGamePlayerName);
        this.colorRadioGroupJoin = (RadioGroup) this.findViewById(R.id.radioGroupColorJoinGame);


        this.netListener = new NetListener(this);
        NetClientFacade.getInstance().addListener(this.netListener);

        this.toggleStatus(true, "Connecting to server...");
        NetClientFacade.getInstance().connectToServer("192.168.2.102", 8080);

    }

    @Override
    protected void onDestroy() {
        NetClientFacade.getInstance().removeListener(this.netListener);
        super.onDestroy();
    }

    private void startGame(String p1Name, String p1Color, String p2Name, String p2Color, String localPlayerName){
        Intent intent = new Intent(MPScreen.this,Spielbrett.class);

        intent.putExtra("nameOne",p1Name);
        intent.putExtra("nameTwo",p2Name);
        intent.putExtra("colorOne",p1Color);
        intent.putExtra("colorTwo",p2Color);
        intent.putExtra("localPlayerName", localPlayerName);

        this.startActivity(intent);
    }



    public void onCreateGameClicked(View v){
        this.toggleStatus(true, "Creating game");

        String gameName = this.newGameName.getText().toString();
        eColor playerColor = eColor.blue;

        if (this.colorRadioGroup.getCheckedRadioButtonId() == R.id.newGameRed){
            playerColor = eColor.red;
        }

        this.p1Name = gameName;
        this.p1Color = eColString.convertFromECol(playerColor);

        NetClientFacade.getInstance().newGame(gameName, playerColor);
    }

    public void onJoinGameClicked(View v){
        this.toggleStatus(true, "Attempting to join game");

        String gameName = this.joinGameName.getText().toString();
        String playerName = this.joinGamePlayerName.getText().toString();

        eColor playerColor = eColor.blue;

        if (this.colorRadioGroupJoin.getCheckedRadioButtonId() == R.id.newGameRedJoin){
            playerColor = eColor.red;
        }

        this.p2Name = playerName;
        this.p2Color = eColString.convertFromECol(playerColor);
        NetClientFacade.getInstance().joinGame(gameName, playerName, playerColor);

    }

    private void toggleStatus(boolean enable, String statusText){
        if (enable){
            this.progressBar.setVisibility(View.VISIBLE);
            this.statusText.setVisibility(View.VISIBLE);
            this.statusText.setText(statusText == null ? "" : statusText);
        } else {
            if (statusText != null){
                this.statusText.setText(statusText);
            } else {
                this.statusText.setVisibility(View.GONE);
            }

            this.progressBar.setVisibility(View.GONE);
        }
    }
}
