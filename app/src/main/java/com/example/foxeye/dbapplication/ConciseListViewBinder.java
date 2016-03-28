package com.example.foxeye.dbapplication;

import android.app.LauncherActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class ConciseListViewBinder implements SimpleCursorAdapter.ViewBinder {

    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        int viewID = view.getId();
        switch (viewID) {
            case R.id.noodle_name:
                TextView viewName = (TextView) view;
                viewName.setText(cursor.getString(cursor.getColumnIndex(Noodle.KEY_name)));
                break;
            case R.id.noodle_rank:
                TextView viewRank = (TextView) view;
                viewRank.setText(cursor.getString(cursor.getColumnIndex(Noodle.KEY_rank)));
                break;
            case R.id.thumbnail:
                ImageView imageView = (ImageView) view;
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(Noodle.KEY_image));
                if (imageBytes != null) {
                    Bitmap bit = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imageView.setImageBitmap(bit);
                } else {
                    imageView.setImageBitmap(null);
                }
                break;
        }
        return true;
    }
}
