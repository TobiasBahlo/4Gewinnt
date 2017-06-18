package com.bahlot.a4gewinnt.frontend;

import android.app.Activity;
import android.os.Bundle;
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
            toggleStatus(false, reason);
            Toast.makeText(this.hostActivity, "Game creation failed! " + reason, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onJoinedGame(String gameName, String firstPlayerName, eColor firstPlayerColor) {
            super.onJoinedGame(gameName, firstPlayerName, firstPlayerColor);
        }

        @Override
        public void onJoinGameFailed(String reason) {
            super.onJoinGameFailed(reason);
        }
    }

    ProgressBar progressBar;
    TextView statusText;
    NetListener netListener;

    EditText newGameName;
    EditText joinGameName;

    RadioGroup colorRadioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mp_screen);

        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        this.statusText = (TextView) this.findViewById(R.id.statusText);
        this.newGameName = (EditText) this.findViewById(R.id.newGameP1Name);
        this.colorRadioGroup = (RadioGroup) this.findViewById(R.id.radioGroupColor);
        this.joinGameName = (EditText) this.findViewById(R.id.joinGameName);


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

    public void onCreateGameClicked(View v){
        this.toggleStatus(true, "Creating game");

        String gameName = this.newGameName.getText().toString();
        eColor playerColor = eColor.blue;

        if (this.colorRadioGroup.getCheckedRadioButtonId() == R.id.newGameRed){
            playerColor = eColor.red;
        }

        NetClientFacade.getInstance().newGame(gameName, playerColor);
    }

    public void onJoinGameClicked(View v){
        this.toggleStatus(true, "Attempting to join game");

        String gameName = this.joinGameName.getText().toString();

        NetClientFacade.getInstance().joinGame(gameName, "Peter", eColor.none);

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
