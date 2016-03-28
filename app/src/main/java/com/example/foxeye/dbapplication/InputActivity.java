package com.example.foxeye.dbapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Target;

public class InputActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button okbtn;
    Button cancelbtn;
    EditText editTextName;
    EditText editTextRank;
    EditText editTextComment;
    ImageView photView;
    private int _Noodle_Id = 0;

    Bitmap bit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        okbtn = (Button)findViewById(R.id.btnok);
        cancelbtn = (Button)findViewById(R.id.btncancel);
        photView = (ImageView) this.findViewById(R.id.phot);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextRank = (EditText)findViewById(R.id.editTextRank);
        editTextComment = (EditText)findViewById(R.id.editTextComment);

        okbtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        photView.setOnClickListener(this);

        _Noodle_Id = 0;
        Intent intent = getIntent();
        _Noodle_Id = intent.getIntExtra("noodle_Id", 0);
        NoodleRepo repo = new NoodleRepo(this);
        Noodle noodle = new Noodle();
        noodle = repo.getNoodleById(_Noodle_Id);

        editTextComment.setText(noodle.comment);
        editTextRank.setText(String.valueOf(noodle.rank));
        editTextName.setText(noodle.name);

        photView = (ImageView) findViewById(R.id.phot);
        Uri resultUri = intent.getData();
        photView.setImageURI(resultUri);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("","Uri: "+ uri.toString());

                try {
                    bit = getBitmapFromUri(uri);
                    photView.setImageBitmap(bit);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fd = pfd.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fd);
        pfd.close();
        return image;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnok:
                NoodleRepo repo = new NoodleRepo(this);
                Noodle noodle = new Noodle();
                noodle.comment = editTextComment.getText().toString();
                noodle.rank = Integer.parseInt(editTextRank.getText().toString());
                noodle.name = editTextName.getText().toString();
                noodle.noodle_ID = _Noodle_Id;
                if (bit != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    noodle.image = baos.toByteArray();
                }

                if(_Noodle_Id == 0) {
                    _Noodle_Id = repo.insert(noodle);
                    Toast.makeText(this, "New Insert", Toast.LENGTH_SHORT).show();
                }else {
                    repo.update(noodle);
                    Toast.makeText(this, "" +
                            "Record update", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btncancel:
                new NoodleRepo(this).delete(1);
                finish();
                break;


            case R.id.phot:
                Toast.makeText(this,"phot",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);

                break;

        }
    }
}
