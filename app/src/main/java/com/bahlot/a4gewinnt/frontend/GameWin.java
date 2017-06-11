package com.bahlot.a4gewinnt.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class GameWin extends AppCompatActivity implements View.OnClickListener{
    TextView nameWin;
    TextView nameWin2;
    Bundle extra = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extra = getIntent().getExtras();
        int winner;
        String winnerName;
        winner = (int)extra.get("winner");
        winnerName = (String) extra.get("winPlayerName");
        String wincol = (String) extra.get("wincolor");
        String winnerT ;
        if(winner ==1){
            winnerT= "One";
        }else{
            winnerT="Two";
        }
        setContentView(R.layout.activity_game_win);
        nameWin = (TextView) findViewById(R.id.winName);
        nameWin2 = (TextView)findViewById(R.id.winName2);
        String ausg= "Player "+winnerT+" : "+winnerName;
        String ausg2= "with the "+wincol+" coins";
        nameWin.setText(ausg);
        nameWin2.setText(ausg2);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.newGame: startActivity(new Intent(GameWin.this,StartGame.class));
                break;
            case R.id.close :finish();
                System.exit(0); //Exit;
                break;
        }
    }
}