package com.example.dev.mistakeerror.error_component;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dev.mistakeerror.R;
import com.example.dev.mistakeerror.database.DBHelper;
import com.example.dev.mistakeerror.newnote.NewNote;

import java.util.ArrayList;

/**
 * Created by dev on 19.01.17..
 */

public class ErrorFragment extends Fragment {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ErrorAdapter errorAdapter;
    private Button addNoteBTN;

    private ArrayList<String> titles;
    private ArrayList<Item> items;

    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.error_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        addNoteBTN = (Button) view.findViewById(R.id.BTNAddNewNote);

        dbHelper = new DBHelper(getContext());
        setTitles();


        addNoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onClick add Note");
                openNewNote();
            }
        });
    }

    public void openNewNote() {
        Intent intent = new Intent(getContext(), NewNote.class);
//        intent.putExtra("id", items.get(position).getId());
        startActivity(intent);
    }


    public void setTitles() {
        titles = new ArrayList<>();
        items = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        cursor = dbHelper.getNotes(database);
        new CursorLoader(getContext());

        database.close();

        if (cursor.moveToFirst()) {
            do {
                items.add(new Item(cursor.getShort(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        for (Item i : items) {
            titles.add(i.getTitle());

        }
        errorAdapter = new ErrorAdapter(items, getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(errorAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitles();
    }
}
