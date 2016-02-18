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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TopListActivity extends Activity implements View.OnClickListener {

    Button INPUT;
    Button CAMERA;
    Button SHARE;

    DBOpenHelper dbOpenHelper;

    ListView noodleList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.INPUT = (Button)findViewById(R.id.input);
        this.INPUT.setOnClickListener(this);

        dbOpenHelper = new DBOpenHelper(TopListActivity.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select name _id, * from Noodle", null);
        noodleList = (ListView) findViewById(R.id.list);
        noodleList.setAdapter(new SimpleCursorAdapter(this,
                R.layout.view_entry,
                cursor,
                new String[]{"name", "id"},
                new int[]{R.id.noodle_name, R.id.noodle_Id}, 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_list);

        this.INPUT = (Button)findViewById(R.id.input);
        this.INPUT.setOnClickListener(this);

        dbOpenHelper = new DBOpenHelper(TopListActivity.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select name _id, * from Noodle", null);
        noodleList = (ListView) findViewById(R.id.list);
        noodleList.setAdapter(new SimpleCursorAdapter(this,
                R.layout.view_entry,
                cursor,
                new String[]{"name", "id"},
                new int[]{R.id.noodle_name, R.id.noodle_Id}, 0));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input:
                Intent intent = new Intent(TopListActivity.this, InputActivity.class);
                startActivity(intent);
                break;
        }
    }
}


