package com.example.khalil_abbas.thoughSaver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends CursorAdapter {

    DBhelper helper;
    Context con;

    public CustomAdapter(Context context, Cursor c) {
        super(context, c, 0);
        helper = new DBhelper(context);
        con = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
    }


    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        // grabbing the views from xml into java using view.findviewbyid.
        // the variable view represents each item of the listview which is the row.xml

        final TextView thought = (TextView) view.findViewById(R.id.thoughtView);

        ImageView bookmark = (ImageView) view.findViewById(R.id.bookmark);

        thought.setText(cursor.getString(cursor.getColumnIndexOrThrow(helper.column_thought)));

        int isbookmark =
                Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(helper.column_bookmark)));

        if (isbookmark == 0) {

            bookmark.setImageResource(R.drawable.book);
        } else {
            bookmark.setImageResource(R.drawable.book_marked);
        }
        int viewid = cursor.getInt(cursor.getColumnIndexOrThrow(helper.column_id));
        view.setTag(viewid);

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                final int id = (int) view.getTag();

                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setIcon(R.drawable.boy);
                builder.setTitle("Delete Thought");
                builder.setMessage("Are you sure you want to delete the thought?");
                builder.setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                helper.deleteThought(id);
                                CustomAdapter.this.changeCursor(helper.getThoughts());
                                notifyDataSetChanged();

                                // if we delete them all, we want the"you have no thoughts"toappearagain
                                // onResume in the main activity is not called after dialog dismissal because
                                // it's not another activity but only a dialog which is a part of the UI
                                // so we have to call onresume ourselves
                                // cast the context to MainActivity and call onResume manually.
                                ((ThoughtSaver) con).onResume();

                            }
                        });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                Dialog d = builder.create();
                d.show();
                return false;
            }

            //    @Override
            //   public void onClick(final View view) {


            //  }
        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int id = (int) view.getTag();

                // TODO add edit function
                Intent toEditActivity = new Intent(con, EditThoughtActivity.class);
                toEditActivity.putExtra("content",thought.getText().toString() );
                toEditActivity.putExtra("id",id);

                con.startActivity(toEditActivity);
            }
        });
        // imageview (bookmark) onClick listener.
        // When clicked, it toggles between open and closed

        bookmark.setTag(viewid);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.toggleBookmark((int) view.getTag());
                // update the adapter to re-draw the listview.

                CustomAdapter.this.changeCursor(helper.getThoughts());
                notifyDataSetChanged();
            }
        });

    }

}

