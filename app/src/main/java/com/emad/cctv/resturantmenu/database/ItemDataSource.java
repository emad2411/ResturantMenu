package com.emad.cctv.resturantmenu.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/* in this class we are doing the operation on our table
* if we have more than one table then we can creat more than one class each class for one table
* in this class we are calling the DBHelper class  create the datbase,open and close the connection to the database
  * it is recommended to get the WritableDatabase here in this class rather than get it in the main activity
  * in addition in this class we are having a methods to add remove and update items
  * */


public class ItemDataSource {
    //here we are declaring a member classes of DBHelper and SQLiteDatabase
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
// constructor
    public ItemDataSource(Context context) {
        mContext = context;
        mDBHelper=new DBHelper(mContext);
        mSQLiteDatabase= mDBHelper.getWritableDatabase();
    }
//open the connection with the database by getting the WritableDatabase
    public void open(){
        mSQLiteDatabase=mDBHelper.getWritableDatabase();

    }
//each time we need to open we have to close the connection after finish
    public void close(){
        mDBHelper.close();
    }


}
