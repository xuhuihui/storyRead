package com.example.ll.myapplication5;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ll on 2016/12/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_CONTACT = "create table contact("
            +"id integer primary key autoincrement,"
            +"mobile text,"
            +"password text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT);
        ContentValues values=new ContentValues();
        values.put("mobile","17816869707");
        values.put("password","1");
        db.insert("contact", null , values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
