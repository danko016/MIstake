package com.example.dev.mistakeerror.editnote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dev.mistakeerror.R;
import com.example.dev.mistakeerror.database.DBHelper;
import com.example.dev.mistakeerror.newnote.NewNote;

/**
 * Created by dev on 25.01.17..
 */

public class ShowNote extends Activity {

    TextView textViewEditTitle;
    TextView textEditDate;
    TextView textViewEditContent;
    Button editButton;

    private int id = 0;
    private String title = "defaultTitle";
    private String content = "defaultContent";
    private String date = "defaultDate";

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnote_layout);
        textViewEditTitle = (TextView) findViewById(R.id.TVEditTitle);
        textViewEditContent = (TextView) findViewById(R.id.TVEditContent);
        textEditDate = (TextView) findViewById(R.id.TVEditDate);
        editButton = (Button) findViewById(R.id.BTNEdit);

       getNote();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNote();
                Log.d("tag", "open new from edit");
            }
        });

    }

    public void openNewNote() {
        Intent intent = new Intent(this, NewNote.class);
        intent.putExtra("editMode", true);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void getNote(){
        dbHelper = new DBHelper(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", id);
        Log.d("tag", "id " + id);

        db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getNote(db, id);
        db.close();

        title = cursor.getString(0);
        content = cursor.getString(1);
        date = cursor.getString(2);

        textViewEditTitle.setText(title);
        textViewEditContent.setText(content);
        textEditDate.setText(date);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNote();
    }
}