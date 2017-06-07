package com.bahlot.a4gewinnt.Frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ConnectFour extends AppCompatActivity implements View.OnClickListener{
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
        setContentView(R.layout.activity_connect_four);
        //setContentView(R.layout.landscape);
        //setContentView(R.layout.portrait);
        buttonTheExit = (ImageButton) findViewById(R.id.exittoclose);
        buttonTheExit.setOnClickListener(this);
        buttonSinglePStart = (Button) findViewById(R.id.SinglePlayerStart);
        buttonSinglePStart.setOnClickListener(this);
        buttonMultiPStart = (Button) findViewById(R.id.CreateMultiplayer);
        buttonMultiPStart.setOnClickListener(this);
        buttonFacebookAPIConnect = (Button) findViewById(R.id.facebookApiConnect);
        buttonFacebookAPIConnect.setOnClickListener(this);
        buttonOne = (Button) findViewById(R.id.firstRow);
        buttonOne.setOnClickListener(this);
        buttonSecond = (Button) findViewById(R.id.secondRow);
        buttonSecond.setOnClickListener(this);
        buttonThird = (Button) findViewById(R.id.thirdRow);
        buttonThird.setOnClickListener(this);
        buttonFourth = (Button) findViewById(R.id.fourthRow);
        buttonFourth.setOnClickListener(this);
        buttonFith = (Button) findViewById(R.id.fithRow);
        buttonFith.setOnClickListener(this);
        buttonSixth = (Button) findViewById(R.id.sixthRow);
        buttonSixth.setOnClickListener(this);
        buttonSeventh = (Button) findViewById(R.id.seventhRow);
        buttonSeventh.setOnClickListener(this);
        buttonExit = (ImageButton) findViewById(R.id.close);
        buttonExit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.SinglePlayerStart : setContentView(R.layout.landscape);
                //calInterface;
                break;
            case R.id.CreateMultiplayer : //callInterface;
                break;
            case R.id.facebookApiConnect : //callInterface;
                break;
            case R.id.firstRow : //calInterface;
                break;
            case R.id.secondRow : //callInterface;
                break;
            case R.id.thirdRow : //callInterface;
                break;
            case R.id.fourthRow : //callInterface;
                break;
            case R.id.fithRow : //callInterface;
                break;
            case R.id.sixthRow : //callInterface;
                break;
            case R.id.seventhRow : //callInterface;
                break;
            case R.id.close : //Exit;
                break;
            case R.id.exittoclose : //Exit;
                break;

            //case R.id.menu : //Menu;
              //  break;


        }
    }
}
