package com.example.foxeye.dbapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.*;

public class TopListActivity extends Activity {

    Button INPUT;
    Button CAMERA;
    Button SHARE;

    DBOpenHelper dbOpenHelper;

    ListView noodleList;

    NoodleRepo repo = new NoodleRepo(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbOpenHelper = new DBOpenHelper(TopListActivity.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        /*Cursor cursor = db.rawQuery("select name _id, * from Noodle", null);
        noodleList = (ListView) findViewById(R.id.list);
        noodleList.setAdapter(new SimpleCursorAdapter(this,
                R.layout.view_entry,
                cursor,
                new String[]{"name", "id"},
                new int[]{R.id.noodle_name, R.id.noodle_Id}, 0));*/

        Button inputButton = (Button) findViewById(R.id.input);
        inputButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopListActivity.this, InputActivity.class);
                startActivity(intent);

                Toast.makeText(TopListActivity.this, "test", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_list);

        dbOpenHelper = new DBOpenHelper(TopListActivity.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select id _id, name _id, rank _id, comment _id, * from Noodle", null);
        noodleList = (ListView) findViewById(R.id.list);
        noodleList.setAdapter(new SimpleCursorAdapter(this,
                R.layout.view_entry,
                cursor,
                new String[]{"name", "id", "rank", "comment"},
                new int[]{R.id.noodle_name, R.id.noodle_Id, R.id.noodle_rank, R.id.noodle_comment}, 0));

        Button inputButton = (Button) findViewById(R.id.input);
        inputButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopListActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        noodleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopListActivity.this, DetailActivity.class);
                Cursor selectedItem = (Cursor) noodleList.getItemAtPosition(position);

                int lgin_id = selectedItem.getInt(selectedItem.getColumnIndex("id"));
                String lgin_name = selectedItem.getString(selectedItem.getColumnIndex("name"));

                intent.putExtra(lgin_name, lgin_id);

                startActivity(intent);
                //Toast.makeText(TopListActivity.this, "test", Toast.LENGTH_LONG).show();
            }
        });
    }
}


