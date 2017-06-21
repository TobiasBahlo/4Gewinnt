package com.bahlot.a4gewinnt.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Highscore extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }
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
