package com.example.dev.mistakeerror.database;

/**
 * Created by dev on 23.01.17..
 */

public class Item {
    private int id;
    private  String title;
    private String date;

    public Item(int id, String title, String date){
        this.id = id;
        this.title = title;
        this.date = date;

    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }
}
