package com.bahlot.a4gewinnt.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import com.bahlot.a4gewinnt.frontend.Highscore;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Leonid on 09.06.2017.
 */

public class VierGewinntDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "vierGewinnt2.db";         // Datenbankname
    public static final String TABLE_NAME = "viergewinnt_table";    // Viergewinnt Tabelle
    public static final int DB_VERSION = 1;                         // Datenbank Version

    // Spalten
    //Für vierGewinnt.db
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "SCORE";
    public static final String COL_3 = "FILENAME";

    // Constructor, beim Aufrufen erstellt die DB
    public VierGewinntDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase(); //Zu testzwecken, testet ob DB erstellt wird
        db.execSQL("create table if not exists " +TABLE_NAME + "(" +
                "NAME TEXT PRIMARY KEY, " +
                "SCORE INTEGER, " +
                "FILENAME TEXT) ");
    }

    //Für vierGewinnt2.db

    // Erstellt die Tabelle
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " +TABLE_NAME + "(" +
                "NAME TEXT PRIMARY KEY, " +
                "SCORE INTEGER, " +
                "FILENAME TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Player in die Tabelle einfügen
    public boolean insert_Player(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    // SCORE in die Tabelle einfügen
    public boolean insert_SCORE(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, score);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Spielstand in die Tabelle einfügen
    public boolean insert_Filename(String filename) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, filename);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Score und Player in die Tabelle einfügen
    public boolean insert_Data(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, score);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    // Daten ändern
    public boolean updateData(String name, int score) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COL_2}, COL_1 + "=\""+name +"\"", null, null, null, null);

        int oldScore = 0;
        if (c.moveToFirst() && c.getCount() > 0){
            oldScore = c.getInt(c.getColumnIndex(COL_2));
        }
        score += oldScore;
        c.close();
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, score);
        int numrows = db.update(TABLE_NAME, contentValues, "NAME = ?", new String[] { name });
        return numrows > 0;
    }


    // Spieler und Score aus der Tabele ausgeben
    public Cursor showHighscore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dataNameAndScore = db.query(TABLE_NAME, new String[]{COL_1, COL_2}, null, null, null, null, COL_2 + " DESC");//db.rawQuery("SELECT * FROM " +TABLE_NAME+" ORDER BY " +COL_2+" DESC", null);
        return dataNameAndScore;
    }




    // Maybe we'll need it )))
/*
    public void delete_VIERGEWINNT(String nameOneET, Integer score, String filename) {
        this.getWritableDatabase().delete("viergewinnt_table","NAME='"+nameOneET+"'",null);
        this.getWritableDatabase().delete("viergewinnt_table","SCORE='"+score+"'",null);
        this.getWritableDatabase().delete("viergewinnt_table","filename='"+filename+"'",null);
    }

    public void update_VIERGEWINNT(String old_filename, String new_filename) {
        this.getWritableDatabase().execSQL("viergewinnt_table SET FILENAME='"+new_filename+"'WHERE FILENAME='"+old_filename+"'");
    }

    public void list_all_Player(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TABLE_VIERGEWINNT", null);
        textView.setText("");
        while(cursor.moveToNext()) {
            textView.append(cursor.getString(0) + "" +cursor.getString(1)+"\n"); //0 = PLAYER, 1 = SCORE
        }
        */


}
