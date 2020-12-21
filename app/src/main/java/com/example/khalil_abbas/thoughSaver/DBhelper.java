package com.example.khalil_abbas.thoughSaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




    public class DBhelper extends SQLiteOpenHelper {


    String column_id = "_id";
    String column_thought = "thought";
    String column_bookmark = "bookmark";



    public DBhelper(Context context) {
        super(context, "thoughtsDB", null, 1);


    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creationquery = "CREATE TABLE thoughts(_id INTEGER PRIMARY KEY, " +
        "thought TEXT, bookmark INT)";
        sqLiteDatabase.execSQL(creationquery);
        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}



    public Cursor getThoughts(){

        // get database instant
        SQLiteDatabase db = this.getReadableDatabase();

        // initialize query
        String query = "SELECT * FROM thoughts";

        // execute query and get cursor object (array of data)
        return db.rawQuery(query,null);

    }

        public void editThought(String newThought,int id){ // insert into ...

            // write into db
            SQLiteDatabase db= this.getWritableDatabase();


            // initially it's not bookmarked.
            ContentValues cv = new ContentValues();

            // execute query and put the data in cv
            cv.put(this.column_id,id);
            cv.put(this.column_thought,newThought);
            cv.put(this.column_bookmark,0);


            // exute query and write into db
          //  db.update("thoughts",cv,this.column_id+id,null);
            // exute query and write into db
          //  db.insert("thoughts",null,cv);
          //  String strSQL = "UPDATE thoughts SET "+" = someValue WHERE "+" = "+ someValue;
            String strSQL = "UPDATE thoughts SET " + this.column_thought +"='"+newThought + "' WHERE "+ this.column_id + "='" +id+"'";

            db.execSQL(strSQL);
            // close connection
            db.close();

        }

    public void addThought(String newThought){ // insert into ...

        // write into db
        SQLiteDatabase db= this.getWritableDatabase();


        // initially it's not bookmarked.
        ContentValues cv = new ContentValues();

        // execute query and put the data in cv
        cv.put(this.column_thought,newThought);
        cv.put(this.column_bookmark,0);

        // exute query and write into db
        db.insert("thoughts",null,cv);

        // close connection
        db.close();

    }


    public void deleteThought(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("thoughts","_id=?",new String[]{id+""});
        db.close();
    }


    public void toggleBookmark(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT bookmark FROM thoughts WHERE _id=?", new String[]{id+""});
        c.moveToFirst();
        int currentstate = c.getInt(0); // cursor is one column and 1 row (1 cell only)
        ContentValues cv = new ContentValues();
        if(currentstate==0){
            cv.put("bookmark",1);
        }     else {
            cv.put("bookmark",0);
        }
        db.update("thoughts",cv,"_id=?",new String[]{id+""});

        db.close();
    }





}


