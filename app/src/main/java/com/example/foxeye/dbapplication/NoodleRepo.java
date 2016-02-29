package com.example.foxeye.dbapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class NoodleRepo {
    private DBOpenHelper dbOpenHelper;

    public NoodleRepo(Context context){
        dbOpenHelper = new DBOpenHelper(context);
    }

    public  int insert(Noodle noodle){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Noodle.KEY_image, noodle.image);
        values.put(Noodle.KEY_date, noodle.date);
        values.put(Noodle.KEY_comment, noodle.comment);
        values.put(Noodle.KEY_rank, noodle.rank);
        values.put(Noodle.KEY_name, noodle.name);

        long noodle_Id = db.insert(Noodle.TABLE, null, values);
        db.close();
        return (int)noodle_Id;
    }


    public void delete(int noodle_Id){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(Noodle.TABLE, Noodle.KEY_ID + "=?", new String[]{ String.valueOf(noodle_Id)});
        db.close();
    }

    public  void  update(Noodle noodle){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Noodle.KEY_comment, noodle.comment);
        values.put(Noodle.KEY_rank, noodle.rank);
        values.put(Noodle.KEY_name, noodle.name);

        db.update(Noodle.TABLE, values, Noodle.KEY_ID + "=?", new String[]{ String.valueOf(noodle.noodle_ID)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getNoodleList(){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Noodle.KEY_ID + "," +
                Noodle.KEY_name + "," +
                Noodle.KEY_rank + "," +
                Noodle.KEY_comment + "," +
                Noodle.KEY_date + "," +
                Noodle.KEY_image +
                " FROM " + Noodle.TABLE;

        ArrayList<HashMap<String, String>> noodleList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> noodle = new HashMap<String, String>();
                noodle.put("id", cursor.getString(cursor.getColumnIndex(Noodle.KEY_ID)));
                noodle.put("name", cursor.getString(cursor.getColumnIndex(Noodle.KEY_name)));
                noodleList.add(noodle);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return noodleList;
    }

    public  Noodle getNoodleById(int Id){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Noodle.KEY_ID + "," +
                Noodle.KEY_name + "," +
                Noodle.KEY_rank + "," +
                Noodle.KEY_comment + "," +
                Noodle.KEY_date + "," +
                Noodle.KEY_image +
                " FROM " + Noodle.TABLE +
                " WHERE "+
                Noodle.KEY_ID + "=?";

        int iCount = 0;
        Noodle noodle = new Noodle();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id)});

        if (cursor.moveToFirst()){
            do {
                noodle.noodle_ID = cursor.getInt(cursor.getColumnIndex(Noodle.KEY_ID));
                noodle.name = cursor.getString(cursor.getColumnIndex(Noodle.KEY_name));
                noodle.rank = cursor.getInt(cursor.getColumnIndex(Noodle.KEY_rank));
                noodle.comment = cursor.getString(cursor.getColumnIndex(Noodle.KEY_comment));
                noodle.date = cursor.getInt(cursor.getColumnIndex(Noodle.KEY_date));
                noodle.image = cursor.getString(cursor.getColumnIndex(Noodle.KEY_image));

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return  noodle;

    }
}
