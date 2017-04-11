package com.example.dev.mistakeerror.newnote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dev.mistakeerror.R;
import com.example.dev.mistakeerror.database.DBHelper;

import java.io.File;
import java.util.Date;

/**
 * Created by dev on 23.01.17..
 */

public class NewNote extends Activity {


    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public EditText editTitle;
    public EditText editContent;
    private Button saveBTN;
    private Button cameraBtn;

    private boolean editMode;
    private int id = 0;
    private static final int CAMERA_IMAGE_REQUEST = 101;
    private String imageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newnote_layout);

        editTitle = (EditText) findViewById(R.id.EDTitle);
        editContent = (EditText) findViewById(R.id.EDContent);
        saveBTN = (Button) findViewById(R.id.BTNSave);
        cameraBtn = (Button) findViewById(R.id.BTNCamera);

        dbHelper = new DBHelper(getApplicationContext());

        //getting intent from ShowNote call activity - edit button clicked
        //must get an id to know which note to edit
        final Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", false);
        id = intent.getIntExtra("id", 0);

        Log.d("tag", "editMode " + editMode);

        //if user is editing, prepare db and editText
        if (editMode) {
            db = dbHelper.getReadableDatabase();
            Cursor c = dbHelper.getNote(db, id);
            db.close();

            //set title and content to view to modify already entered text
            editTitle.setText(c.getString(0));
            editContent.setText(c.getString(1));
        }

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
                captureImage();
            }
        });

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
    public void onBackPressed() {
        super.onBackPressed();
        dbHelper.updateNote(editTitle.getText().toString(), editContent.getText().toString(), id);
        if (editMode) {
            Toast.makeText(getApplicationContext(), R.string.noteSaved, Toast.LENGTH_SHORT).show();
        }
    }

    private void startCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, 1);
    }

    public void captureImage() {

        // Creating folders for Image
        String imageFolderPath = Environment.getExternalStorageDirectory().toString()
                + "/AutoFare";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        imageName = new Date().toString() + ".jpg";

        // Creating image here
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imageFolderPath, imageName)));
        startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
    }
}
