package com.bahlot.a4gewinnt.frontend;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FourConnect extends AppCompatActivity implements View.OnClickListener{
    private ViewSwitcher switchScreen;
    public Button buttonOne;
    public Button buttonSecond;
    public Button buttonThird;
    public Button buttonFourth;
    public Button buttonFith;
    public Button buttonSixth;
    public Button buttonSeventh;
    public ImageButton buttonExit;
    public ImageButton buttonTheExit;
    public Button buttonSinglePStart;
    public Button buttonMultiPStart;
    public Button buttonFacebookAPIConnect;
    public Button buttonHighscore;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private boolean gameRequestPending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_connect);
        firstStart();
        //setContentView(R.layout.landscape);
        //setContentView(R.layout.portrait);
        View view = findViewById(R.id.four_connect_layout);
        loginButton = (LoginButton) view.findViewById(R.id.startFBLogin);
        loginButton.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.v("Lel1", "Success!!!1");

                if (gameRequestPending){
                    processGameRequest(getIntent().getData());
                }
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



        Intent i = getIntent();
        String action = i.getAction();
        Uri data = i.getData();
        if (data != null){
            List<String> req = data.getQueryParameters("request_ids");
            for (String s : req){
                Log.d("FB", s);
            }
            AccessToken token = AccessToken.getCurrentAccessToken();

            if (token == null){
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                this.gameRequestPending = true;
            } else {
                this.processGameRequest(data);
            }

        }


    }

    private void processGameRequest(Uri intentData) {
        List<String> requests = intentData.getQueryParameters("request_ids");
        final String requestId = requests.get(0);

        GraphRequest greq = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(),
               requestId + "_" + Profile.getCurrentProfile().getId(), new GraphRequest.Callback(){
                    @Override
                    public void onCompleted(GraphResponse response) {

                        if (response.getError() != null){
                            Toast.makeText(FourConnect.this, "Failed to join game, invite expired!", Toast.LENGTH_LONG).show();
                        } else {

                            JSONObject obj = response.getJSONObject();
                            try {
                                String dat = obj.getString("data");
                                Intent i = new Intent(FourConnect.this, MPScreen.class);
                                i.putExtra("joinGame", dat);

                                startActivity(i);



                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(FourConnect.this, "Failed to join game, invite expired!", Toast.LENGTH_LONG).show();
                            } finally {
                                GraphRequest.newDeleteObjectRequest(AccessToken.getCurrentAccessToken(),
                                        requestId,
                                        new GraphRequest.Callback(){
                                            @Override
                                            public void onCompleted(GraphResponse response) {

                                            }
                                        }).executeAsync();
                            }
                        }
                    }
                });
        greq.executeAsync();
    }

    public void firstStart() {
        buttonTheExit = (ImageButton) findViewById(R.id.exittoclose);
        //buttonTheExit.setOnClickListener(this);
        buttonSinglePStart = (Button) findViewById(R.id.SinglePlayerStart);
        //buttonSinglePStart.setOnClickListener(this);
        buttonMultiPStart = (Button) findViewById(R.id.CreateMultiplayer);
        //buttonMultiPStart.setOnClickListener(this);
        buttonFacebookAPIConnect = (Button) findViewById(R.id.facebookApiConnect);
        //buttonFacebookAPIConnect.setOnClickListener(this);
        buttonOne = (Button) findViewById(R.id.firstRow);
        //buttonOne.setOnClickListener(this);
        buttonSecond = (Button) findViewById(R.id.secondRow);
        //buttonSecond.setOnClickListener(this);
        buttonThird = (Button) findViewById(R.id.thirdRow);
        //buttonThird.setOnClickListener(this);
        buttonFourth = (Button) findViewById(R.id.fourthRow);
        //buttonFourth.setOnClickListener(this);
        buttonFith = (Button) findViewById(R.id.fithRow);
        //buttonFith.setOnClickListener(this);
        buttonSixth = (Button) findViewById(R.id.sixthRow);
        //buttonSixth.setOnClickListener(this);
        buttonSeventh = (Button) findViewById(R.id.seventhRow);
        //buttonSeventh.setOnClickListener(this);
        buttonExit = (ImageButton) findViewById(R.id.close);
        //buttonExit.setOnClickListener(this);
        buttonHighscore = (Button) findViewById(R.id.ShowHighscore);
    }
    public void setStone(int x,int y){

    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.SinglePlayerStart : startActivity(new Intent(FourConnect.this,StartGame.class));//setContentView(R.layout.landscape);
                //calInterface;

                break;
            case R.id.CreateMultiplayer :
                try
                {
                    startActivity(new Intent(FourConnect.this, MPScreen.class));//callInterface;
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.facebookApiConnect : //callInterface;
                break;
            case R.id.close :finish();
                System.exit(0); //Exit;
                break;
            case R.id.ShowHighscore:
                startActivity(new Intent(FourConnect.this,Highscore.class));
                break;
            case R.id.exittoclose :finish();
                System.exit(0); //Exit;
                break;

            //case R.id.menu : //Menu;
            //  break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
