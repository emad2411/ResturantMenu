package com.emad.cctv.resturantmenu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    private static final int TABLE_VERSION=1;
    private static final String RESTURANT_DB ="restaurant.db";

    public DBHelper(Context context) {
        super(context, RESTURANT_DB, null, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableItems.SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TableItems.SQL_DELETE);
        onCreate(db);

    }
}
