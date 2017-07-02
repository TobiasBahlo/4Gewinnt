package com.bahlot.a4gewinnt.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bahlot.a4gewinnt.backend.VierGewinntDbHelper;
import com.bahlot.a4gewinnt.backend.eColor;


public class StartGame extends AppCompatActivity implements View.OnClickListener {
    private EditText nameOneET;
    private EditText nameTwoET;

    private String nameOne;
    private String nameTwo;

    private RadioButton radioRed;

    private String colorOne;
    private String colorTwo;

    int score1 = 0;
    int score2 = 0;

    VierGewinntDbHelper vgDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        vgDB = new VierGewinntDbHelper(this); ; // ruft den Constructren in DatabaseHelper

        nameOneET = (EditText)findViewById(R.id.nameOne);
        nameTwoET = (EditText)findViewById(R.id.nameTwo);

        radioRed = (RadioButton) findViewById(R.id.redplayerOne);

    }

///////////////////////////////////////////////////

//Player-1
    public void addPlayer_One() {
        String name = nameOneET.getText().toString();
        boolean isInserted = vgDB.insert_Player(name);

        if (isInserted == true)
            Toast.makeText(StartGame.this,"Player-1 Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(StartGame.this,"Player-1 not Inserted", Toast.LENGTH_LONG).show();
    }


//Player-2
    public void addPlayer_Two() {
        String name = nameTwoET.getText().toString();
        boolean isInserted = vgDB.insert_Player(name);

        if (isInserted == true)
            Toast.makeText(StartGame.this,"Player-2 Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(StartGame.this,"Player-2 not Inserted", Toast.LENGTH_LONG).show();
    }

///////////////////////////////////////////////////

    @Override
    public void onClick(View v) {
        nameOne = nameOneET.getText().toString();
        nameTwo = nameTwoET.getText().toString();
        addPlayer_One();
        addPlayer_Two();
        if(radioRed.isChecked()){
            colorOne = "red";
            colorTwo = "blue";
        }else{
            colorOne = "blue";
            colorTwo = "red";
        }

        Intent intent = new Intent(StartGame.this,Spielbrett.class);
        intent.putExtra("nameOne",nameOne.toString());
        intent.putExtra("nameTwo",nameTwo.toString());
        intent.putExtra("colorOne",colorOne);
        intent.putExtra("colorTwo",colorTwo);
        startActivity(intent);

    }
}
