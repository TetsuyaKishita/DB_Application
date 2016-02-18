package com.example.foxeye.dbapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    Button okbtn;
    Button cancelbtn;
    EditText editTextName;
    EditText editTextRank;
    EditText editTextComment;
    private int _Noodle_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        okbtn = (Button)findViewById(R.id.btnok);
        cancelbtn = (Button)findViewById(R.id.btncancel);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextRank = (EditText)findViewById(R.id.editTextRank);
        editTextComment = (EditText)findViewById(R.id.editTextComment);

        okbtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);

        _Noodle_Id = 0;
        Intent intent = getIntent();
        _Noodle_Id = intent.getIntExtra("noodle_Id", 0);
        NoodleRepo repo = new NoodleRepo(this);
        Noodle noodle = new Noodle();
        noodle = repo.getNoodleById(_Noodle_Id);

        editTextComment.setText(noodle.comment);
        editTextRank.setText(String.valueOf(noodle.rank));
        editTextName.setText(noodle.name);
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
        }
    }
}
