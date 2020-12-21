package com.example.khalil_abbas.thoughSaver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddThought extends AppCompatActivity {


    DBhelper db;
    EditText newthought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thought);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBhelper(this);
        newthought = (EditText) findViewById(R.id.newthoughtview);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newthought.getText().toString().isEmpty()) {
                    Toast.makeText(AddThought.this, "Oops! write something", Toast.LENGTH_SHORT).show();
                } else {

                    db.addThought(newthought.getText().toString());
                    finish();
                }
            }
        });
    }


}
