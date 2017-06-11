package com.bahlot.a4gewinnt.frontend;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_connect);
        firstStart();

        //setContentView(R.layout.landscape);
        //setContentView(R.layout.portrait);

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
    }
    public void setStone(int x,int y){

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.SinglePlayerStart : startActivity(new Intent(FourConnect.this,StartGame.class));//setContentView(R.layout.landscape);
                //calInterface;

                break;
            case R.id.CreateMultiplayer : //callInterface;
                break;
            case R.id.facebookApiConnect : //callInterface;
                break;
            case R.id.close :finish();
                System.exit(0); //Exit;
                break;
            case R.id.exittoclose :finish();
                System.exit(0); //Exit;
                break;

            //case R.id.menu : //Menu;
            //  break;


        }
    }
}
