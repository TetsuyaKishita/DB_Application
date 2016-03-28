package com.example.foxeye.dbapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.widget.Toast.*;

public class TopListActivity extends Activity {

    SimpleCursorAdapter adapter;

    Button inputButton;
    Button cameraButton;
    Button SHARE;

    DBOpenHelper dbOpenHelper;

    ListView noodleList;

    NoodleRepo repo = new NoodleRepo(this);

    final Date date = new Date(System.currentTimeMillis());
    final SimpleDateFormat dataFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
    final String fileName = dataFormat.format(date) + ".jpg";
    Uri mSaveUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera", fileName));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbOpenHelper = new DBOpenHelper(TopListActivity.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        inputButton = (Button) findViewById(R.id.input);
        inputButton.setOnClickListener(new View.OnClickListener() {

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


        adapter = new SimpleCursorAdapter(this,
                R.layout.view_entry,
                cursor,
                new String[]{Noodle.KEY_name, Noodle.KEY_rank, Noodle.KEY_comment, Noodle.KEY_image},
                new int[]{R.id.noodle_name, R.id.noodle_rank, R.id.noodle_comment, R.id.thumbnail}, 0);

        adapter.setViewBinder(new ConciseListViewBinder());
        noodleList.setAdapter(adapter);

        final Button inputButton = (Button) findViewById(R.id.input);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopListActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        cameraButton = (Button) findViewById(R.id.camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mSaveUri);
                startActivityForResult(intent, 100);
            }
        });

        noodleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopListActivity.this, DetailActivity.class);
                Cursor selectedItem = (Cursor) noodleList.getItemAtPosition(position);
                int lgin_id = selectedItem.getInt(selectedItem.getColumnIndex("id"));
                intent.putExtra("KEY", lgin_id);
                startActivity(intent);
            }
        });

        noodleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor selectedItem = (Cursor) noodleList.getItemAtPosition(position);
                int select_id = selectedItem.getInt(selectedItem.getColumnIndex("id"));
                alertDialog(select_id);
                return true;
            }
        });
    }

    private void alertDialog(final int selectId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("You want to delete?");
        alert.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repo.delete(selectId);
                        onResume();
                        dialog.cancel();
                    }
                });
        alert.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(TopListActivity.this, InputActivity.class);
        intent.setData(mSaveUri);
        startActivity(intent);
    }
}


