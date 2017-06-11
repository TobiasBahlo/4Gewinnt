package com.bahlot.a4gewinnt.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;



/**
 * Created by Leonid on 09.06.2017.
 */

public class VierGewinntDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "vierGewinnt.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_VIERGEWINNT = "vierGewinnt";

    public static final String PLAYER = "player";
    public static final String WON = "won";
    public static final String LOST = "lost";
    public static final String FILENAME = "filename";


    public static final String SQL_CREATE = "create table " +TABLE_VIERGEWINNT + "(" +
            PLAYER + "text primary key," +
            WON + "integer not null, " +
            LOST + "integer not null, " +
            FILENAME + "text not null.);";

    public VierGewinntDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" +TABLE_VIERGEWINNT);
        onCreate(sqLiteDatabase);
    }

    public void insert_VIERGEWINNT(String player, Integer won, Integer lost, String filename) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PLAYER", player);
        contentValues.put("WON", won);
        contentValues.put("LOST", lost);
        contentValues.put("FILENAME", filename);
        this.getWritableDatabase().insertOrThrow("TABLE_VIERGEWINNT","",contentValues);
    }


 // Maybe we'll need it )))

    public void delete_VIERGEWINNT(String player, Integer won, Integer lost, String filename) {
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","PLAYER='"+player+"'",null);
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","WON='"+won+"'",null);
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","lost='"+lost+"'",null);
        this.getWritableDatabase().delete("TABLE_VIERGEWINNT","filename='"+filename+"'",null);
    }

    public void update_VIERGEWINNT(String old_filename, String new_filename) {
        this.getWritableDatabase().execSQL("TABLE_VIERGEWINNT SET FILENAME='"+new_filename+"'WHERE FILENAME='"+old_filename+"'");
    }

    public void list_all_Player(TextView textView) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TABLE_VIERGEWINNT", null);
        textView.setText("");
        while(cursor.moveToNext()) {
            textView.append(cursor.getString(0) + "" +cursor.getString(2)); //0 = PLAYER, 2 = Lost
        }

    }
}
