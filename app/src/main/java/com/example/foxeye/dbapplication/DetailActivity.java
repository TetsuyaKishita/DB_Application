package com.example.foxeye.dbapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    SQLiteDatabase db;
    String DBshopName;
    int DBrank;
    String DBcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = (new DBOpenHelper(this)).getReadableDatabase();

        Intent intent = getIntent();
        String strData = intent.getStringExtra("name");
        //int intData = intent.getIntExtra("name", 1);
        String sql = "SELECT name, rank, comment FROM Noodle WHERE id = " + strData;
        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            DBshopName = cursor.getString(
                    cursor.getColumnIndex(Noodle.KEY_name));
            DBrank = cursor.getInt(
                    cursor.getColumnIndex(Noodle.KEY_rank));
            DBcomment = cursor.getString(
                    cursor.getColumnIndex(Noodle.KEY_comment));
        }

        TextView shopName = (TextView) findViewById(R.id.selectShopName);
        shopName.setText((String)DBshopName);
        TextView rank = (TextView)findViewById(R.id.selectRank);
        rank.setText(Integer.toString(DBrank));
        TextView comment = (TextView) findViewById(R.id.selectComment);
        comment.setText(DBcomment);


    }
}
