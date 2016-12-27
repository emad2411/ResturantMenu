package com.emad.cctv.resturantmenu.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ItemDataSource {

    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public ItemDataSource(Context context) {
        mContext = context;
        mDBHelper=new DBHelper(mContext);
        mSQLiteDatabase= mDBHelper.getWritableDatabase();
    }

    public void open(){
        mSQLiteDatabase=mDBHelper.getWritableDatabase();

    }

    public void close(){
        mDBHelper.close();

    }


}
