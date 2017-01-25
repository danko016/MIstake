package com.example.dev.mistakeerror.newnote;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote_layout);

        editTitle = (EditText)findViewById(R.id.EDTitle);
        editContent = (EditText) findViewById(R.id.EDContent);
        saveBTN = (Button) findViewById(R.id.BTNSave);

        dbHelper = new DBHelper(getApplicationContext());

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                dbHelper = new DBHelper(getApplicationContext());
                dbHelper.addNote(title,content);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
    }
}
