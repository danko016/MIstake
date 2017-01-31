package com.example.dev.mistakeerror.newnote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dev.mistakeerror.R;
import com.example.dev.mistakeerror.database.DBHelper;

/**
 * Created by dev on 23.01.17..
 */

public class NewNote extends Activity {


    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public EditText editTitle;
    public EditText editContent;
    private Button saveBTN;

    private boolean editMode;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote_layout);

        editTitle = (EditText) findViewById(R.id.EDTitle);
        editContent = (EditText) findViewById(R.id.EDContent);
        saveBTN = (Button) findViewById(R.id.BTNSave);

        dbHelper = new DBHelper(getApplicationContext());

        //getting intent from ShowNote call activity - edit button clicked
        //must get an id to know which note to edit
        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", false);
        id = intent.getIntExtra("id", 0);

        Log.d("tag", "editMode " + editMode);

        //if user is editing, prepare db and editText
        if (editMode) {
            db = dbHelper.getReadableDatabase();
            Cursor c = dbHelper.getNote(db, id);
            db.close();

            //set title and content to view to modify already enterd text
            editTitle.setText(c.getString(0));
            editContent.setText(c.getString(1));
        }

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                //update if editmode is true, sent from ShowNote.class - update, else just add new note
                if (editMode) {
                    Log.d("tag", "editMode entered");
                    dbHelper.updateNote(title, content, id);
                    finish();

                } else {
                    //adding note regulary
                    dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.addNote(title, content);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
    }
}
