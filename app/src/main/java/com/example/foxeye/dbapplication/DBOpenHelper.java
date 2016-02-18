package com.example.foxeye.dbapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yummy.db";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOODLE ="CREATE TABLE " + Noodle.TABLE + "(" + Noodle.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Noodle.KEY_name + " TEXT, " + Noodle.KEY_rank + " INTEGER, " + Noodle.KEY_comment + " TEXT, " + Noodle.KEY_date + " INTEGER, " + Noodle.KEY_image + " TEXT )";
        db.execSQL(CREATE_TABLE_NOODLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Noodle.TABLE);
        onCreate(db);
    }
}
