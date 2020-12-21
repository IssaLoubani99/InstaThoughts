package com.example.khalil_abbas.thoughSaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditThoughtActivity extends AppCompatActivity {
    EditText thoughtContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thought);


        String content = getIntent().getExtras().getString("content");
        final int id = getIntent().getExtras().getInt("id");

        thoughtContent = (EditText) findViewById(R.id.thoughtContent);

        thoughtContent.setText(content);

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // save new content
                DBhelper db = new DBhelper(getApplicationContext());
                db.editThought(thoughtContent.getText().toString(), id);
                finish();
            }
        });
    }
}
