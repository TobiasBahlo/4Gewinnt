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

// Fügt Player in die Tabelle
    public void addPlayer() {
        boolean isInserted = vgDB.insert_Player(nameOneET.getText().toString()); // insert_Player aus DatabaseHelper
        if (isInserted == true)
            Toast.makeText(StartGame.this,"Player Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(StartGame.this,"Player not Inserted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        nameOne = nameOneET.getText().toString();
        nameTwo = nameTwoET.getText().toString();
        addPlayer();
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
