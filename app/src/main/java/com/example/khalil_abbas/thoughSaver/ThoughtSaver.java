package com.example.khalil_abbas.thoughSaver;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ThoughtSaver extends AppCompatActivity {

    ListView lv;
    TextView nothoughts;
    Cursor c;
    DBhelper db;
    CustomAdapter ca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_saver_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.listview);

        nothoughts = (TextView) findViewById(R.id.nothoughtsyet);


        // initialize database
        db = new DBhelper(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ThoughtSaver.this, AddThought.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {

            // TODO add action to about

            startActivity(new Intent(getApplicationContext(),AboutActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        c = db.getThoughts();
        ca = new CustomAdapter(this, c);
        lv.setAdapter(ca);
        //Toast.makeText(this, c.getCount()+"",Toast.LENGTH_SHORT).show();
        if (c.getCount() != 0) {

            nothoughts.setVisibility(View.INVISIBLE);
        } else {
            nothoughts.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }


}
