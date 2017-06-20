package com.bahlot.a4gewinnt.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bahlot.a4gewinnt.backend.eColor;
import com.bahlot.a4gewinnt.net.NetClientFacade;
import com.bahlot.a4gewinnt.net.NetGameListener;
import com.bahlot.a4gewinnt.net.eColString;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

import org.json.JSONException;
import org.json.JSONObject;


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
            newGamePending = false;
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

    private ProgressBar progressBar;
    private TextView statusText;

    private NetListener netListener;
    private EditText newGameName;
    private EditText joinGameName;

    private EditText joinGamePlayerName;
    private RadioGroup colorRadioGroup;
    private RadioGroup colorRadioGroupJoin;

    private String p1Name;
    private String p1Color;

    private String p2Name;
    private String p2Color;

    private boolean newGamePending;

    private LoginButton fbLoginBtn;
    private Button fbInviteBtn;

    private GameRequestDialog requestDialog;
    private CallbackManager callbackManager;


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

        this.callbackManager = CallbackManager.Factory.create();
        this.requestDialog = new GameRequestDialog(this);

        this.fbLoginBtn = (LoginButton) this.findViewById(R.id.mp_fb_login);
        this.fbInviteBtn = (Button) this.findViewById(R.id.mp_fb_invite);

        this.toggleFBLogin();
        this.setupFBCallbacks();
        this.toggleInviteButton();


        this.netListener = new NetListener(this);
        NetClientFacade.getInstance().addListener(this.netListener);

        this.toggleStatus(true, "Connecting to server...");
        NetClientFacade.getInstance().connectToServer("192.168.2.102", 8080);

        String joinGame = getIntent().getStringExtra("joinGame");
        if (joinGame != null){
            try {
                JSONObject obj = new JSONObject(joinGame);

                String gameName = obj.getString("gameName");
                String usedColor = obj.getString("usedColor");

                eColor myColor = eColor.red;

                if (myColor == eColString.convertToECol(usedColor)){
                    myColor = eColor.blue;
                }

                this.toggleStatus(true, "Attempting to join game");


                this.p2Name = Profile.getCurrentProfile().getLastName();
                this.p2Color = eColString.convertFromECol(myColor);
                NetClientFacade.getInstance().joinGame(gameName, this.p2Name, myColor);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void toggleFBLogin() {


        if (AccessToken.getCurrentAccessToken() != null){
            fbLoginBtn.setVisibility(View.GONE);
        } else {
            fbLoginBtn.setVisibility(View.VISIBLE);
        }
    }

    private void setupFBCallbacks() {
        requestDialog.registerCallback(callbackManager,
                new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        String id = result.getRequestId();
                        Log.d("FB", "Success invite " + id);
                        Toast.makeText(MPScreen.this, "Successfully send game invite!", Toast.LENGTH_LONG).show();
                    }
                    public void onCancel() {
                        Log.v("FB", "Invite canceled");
                    }
                    public void onError(FacebookException error) {
                        Toast.makeText(MPScreen.this, "Failed to send game invite!", Toast.LENGTH_LONG).show();
                    }
                }
        );

        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.v("Lel1", "Success!!!1");
                toggleFBLogin();
                toggleInviteButton();
                Toast.makeText(MPScreen.this, "Login successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void toggleInviteButton() {
        if (this.newGamePending){
            if (AccessToken.getCurrentAccessToken() != null){
                this.fbInviteBtn.setVisibility(View.VISIBLE);
            }
        } else {
            this.fbInviteBtn.setVisibility(View.GONE);
        }
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
        this.newGamePending = true;
        this.toggleInviteButton();
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

    public void onInvitedFriendClicked(View v){

        JSONObject obj = new JSONObject();
        try {
            obj.put("gameName", this.p1Name);
            obj.put("usedColor", this.p1Color);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GameRequestContent content = new GameRequestContent.Builder()
                .setMessage("Come play this level with me")
                .setData(obj.toString())
                .build();
        requestDialog.show(content);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
