package com.bahlot.a4gewinnt.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Leonid on 09.06.2017.
 */

public class VierGewinntDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "vierGewinnt.db";
    public static final String TABLE_NAME = "viergewinnt_table";
    public static final int DB_VERSION = 1;

    // Spalten
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "SCORE";
    public static final String COL_3 = "FILENAME";

    // Constructor, beim aufrufen erstellt die DB
    public VierGewinntDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
//        SQLiteDatabase db = this.getWritableDatabase(); //Zu testzwecken, testet ob DB erstellt wird
    }

    // Erstellt die Tabelle
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME + "(" +
         //       "NAME TEXT UNIQUE PRIMARY KEY, " +
                "NAME TEXT PRIMARY KEY, " +
                "SCORE TEXT, " +
                "FILENAME TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
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

    // Score in die Tabelle einfügen
    public boolean insert_Score(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, score);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Daten aus der Tabele ausgeben
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT NAME FROM " +TABLE_NAME, null);
        return data;
    }



 // Maybe we'll need it )))
/*
    public void delete_VIERGEWINNT(String nameOneET, Integer score, String filename) {
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","NAME='"+nameOneET+"'",null);
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","SCORE='"+score+"'",null);
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","filename='"+filename+"'",null);
    }

    public void update_VIERGEWINNT(String old_filename, String new_filename) {
        this.getWritableDatabase().execSQL("TABLE_VIERGEWINNT SET FILENAME='"+new_filename+"'WHERE FILENAME='"+old_filename+"'");
    }

    public void list_all_Player(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TABLE_VIERGEWINNT", null);
        textView.setText("");
        while(cursor.moveToNext()) {
            textView.append(cursor.getString(0) + "" +cursor.getString(1)+"\n"); //0 = PLAYER, 1 = SCORE
        }
        */


}
