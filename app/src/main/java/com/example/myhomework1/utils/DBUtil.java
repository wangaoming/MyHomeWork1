package com.example.myhomework1.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myhomework1.entity.Insert;

public class DBUtil extends SQLiteOpenHelper {
    private static DBUtil instance;
    public DBUtil(Context context) {
        super(context, "insert1.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( Insert.TBL_Insert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int i1) {
        db.execSQL("drop table if exists insert1" );
        onCreate(db);
    }


}
