package com.bahlot.a4gewinnt.frontend;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bahlot.a4gewinnt.backend.VierGewinntDbHelper;

public class Highscore extends AppCompatActivity implements View.OnClickListener{
    VierGewinntDbHelper vgDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        vgDB = new VierGewinntDbHelper(this); ; // ruft den Constructren in DatabaseHelper

    //    viewData();
    }
/*
    // Daten anzeigen
    public void viewData() {
        Cursor res = vgDB.getData();
        if(res.getCount() == 0) { // 0 == keine Daten in der DB
            // show message
            showMessage("Error", "No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append(res.getString(0)+"\n");
            buffer.append(res.getString(1)+"\n");
        }
        // show all data
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
*/

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.close :finish();
                System.exit(0); //Exit;
                break;
            //case R.id.menu : //Menu;
            //  break;


        }
    }
}
