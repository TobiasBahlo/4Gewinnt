package com.bahlot.a4gewinnt.frontend;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bahlot.a4gewinnt.backend.VierGewinntDbHelper;

public class Highscore extends AppCompatActivity implements View.OnClickListener{
    VierGewinntDbHelper vgDB;

    EditText firstHS;
    EditText firstHSP;

    EditText secondHS;
    EditText secondHSP;

    EditText thirdHS;
    EditText thirdHSP;

    EditText fourthHS;
    EditText fourthHSP;

    EditText fivthHS;
    EditText fivthHSP;

    EditText sixthHS;
    EditText sixthHSP;

    EditText seventhHS;
    EditText seventhHSP;

    EditText eigthHS;
    EditText eigthHSP;

    EditText ninethHS;
    EditText ninethHSP;

    EditText tenthHS;
    EditText tenthHSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        firstHS = (EditText)findViewById(R.id.first);
        firstHSP = (EditText)findViewById(R.id.firstP);

        secondHS = (EditText)findViewById(R.id.second);
        secondHSP = (EditText)findViewById(R.id.secondP);

        thirdHS = (EditText)findViewById(R.id.third);
        thirdHSP = (EditText)findViewById(R.id.thirdP);

        fourthHS = (EditText)findViewById(R.id.fourth);
        fourthHSP = (EditText)findViewById(R.id.fourthP);

        fivthHS = (EditText)findViewById(R.id.fivth);
        fivthHSP = (EditText)findViewById(R.id.fivthP);

        sixthHS = (EditText)findViewById(R.id.sixth);
        sixthHSP = (EditText)findViewById(R.id.sixthP);

        seventhHS = (EditText)findViewById(R.id.seventh);
        seventhHSP = (EditText)findViewById(R.id.seventhP);

        eigthHS = (EditText)findViewById(R.id.eigth);
        eigthHSP = (EditText)findViewById(R.id.eigthP);

        ninethHS = (EditText)findViewById(R.id.nineth);
        ninethHSP = (EditText)findViewById(R.id.ninethP);

        tenthHS = (EditText)findViewById(R.id.tenth);
        tenthHSP = (EditText)findViewById(R.id.tenthP);

        vgDB = new VierGewinntDbHelper(this); ; // ruft den Constructren in DatabaseHelper


        viewHighscore();
    }

///////////////////////////////////////////////
// Highscore anzeigen
    public void viewHighscore() {
        Cursor data = vgDB.showHighscore();

        StringBuffer buffer1A = new StringBuffer();
        StringBuffer buffer1B = new StringBuffer();

        StringBuffer buffer2A = new StringBuffer();
        StringBuffer buffer2B = new StringBuffer();

        StringBuffer buffer3A = new StringBuffer();
        StringBuffer buffer3B = new StringBuffer();

        StringBuffer buffer4A = new StringBuffer();
        StringBuffer buffer4B = new StringBuffer();

        StringBuffer buffer5A = new StringBuffer();
        StringBuffer buffer5B = new StringBuffer();

        StringBuffer buffer6A = new StringBuffer();
        StringBuffer buffer6B = new StringBuffer();

        StringBuffer buffer7A = new StringBuffer();
        StringBuffer buffer7B = new StringBuffer();

        StringBuffer buffer8A = new StringBuffer();
        StringBuffer buffer8B = new StringBuffer();

        StringBuffer buffer9A = new StringBuffer();
        StringBuffer buffer9B = new StringBuffer();

        StringBuffer buffer10A = new StringBuffer();
        StringBuffer buffer10B = new StringBuffer();



        int x = 1;

        while (data.moveToNext() & x == 1) {
            buffer1A.append(data.getString(0));
            buffer1B.append(data.getString(1));

            firstHS.setText(buffer1A.toString());
            firstHSP.setText(buffer1B.toString());

            x++;
        }

        while (data.moveToNext() & x == 2) {
            buffer2A.append(data.getString(0));
            buffer2B.append(data.getString(1));

            secondHS.setText(buffer2A.toString());
            secondHSP.setText(buffer2B.toString());

            x++;
        }

        while (data.moveToNext() & x == 3) {
            buffer3A.append(data.getString(0));
            buffer3B.append(data.getString(1));

            thirdHS.setText(buffer3A.toString());
            thirdHSP.setText(buffer3B.toString());

            x++;
        }

        while (data.moveToNext() & x == 4) {
            buffer4A.append(data.getString(0));
            buffer4B.append(data.getString(1));

            fourthHS.setText(buffer4A.toString());
            fourthHSP.setText(buffer4B.toString());

            x++;
        }

        while (data.moveToNext() & x == 5) {
            buffer5A.append(data.getString(0));
            buffer5B.append(data.getString(1));

            fivthHS.setText(buffer5A.toString());
            fivthHSP.setText(buffer5B.toString());

            x++;
        }

        while (data.moveToNext() & x == 6) {
            buffer6A.append(data.getString(0));
            buffer6B.append(data.getString(1));

            sixthHS.setText(buffer6A.toString());
            sixthHSP.setText(buffer6B.toString());

            x++;
        }

        while (data.moveToNext() & x == 7) {
            buffer7A.append(data.getString(0));
            buffer7B.append(data.getString(1));

            seventhHS.setText(buffer7A.toString());
            seventhHSP.setText(buffer7B.toString());

            x++;
        }

        while (data.moveToNext() & x == 8) {
            buffer8A.append(data.getString(0));
            buffer8B.append(data.getString(1));

            eigthHS.setText(buffer8A.toString());
            eigthHSP.setText(buffer8B.toString());

            x++;
        }

        while (data.moveToNext() & x == 9) {
            buffer9A.append(data.getString(0));
            buffer9B.append(data.getString(1));

            ninethHS.setText(buffer9A.toString());
            ninethHSP.setText(buffer9B.toString());

            x++;
        }

        while (data.moveToNext() & x == 10) {
            buffer10A.append(data.getString(0));
            buffer10B.append(data.getString(1));

            tenthHS.setText(buffer10A.toString());
            tenthHSP.setText(buffer10B.toString());

            x++;
        }
    }

///////////////////////////////////////////////


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
