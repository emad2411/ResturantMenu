package com.emad.cctv.resturantmenu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*to save our menu items in the data base then we need to create a sqlLite database
* here we are creating a DBHelper which extends SQLiteOpenHelper
* in this class we are creating the data base and update it only
* if we want any changes or addition iun this case ItemDataSourcs class will be responsible
* please see the explanation there */



public class DBHelper extends SQLiteOpenHelper {
    /* all the queries String ,table name and  column names we will create it in the TableItems Class
      * please see the explanation there  */


    private static final int TABLE_VERSION=1;
    private static final String RESTURANT_DB ="restaurant.db";

// constructor of the DBHelper class
    public DBHelper(Context context) {
        super(context, RESTURANT_DB, null, TABLE_VERSION);
    }

    // create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableItems.SQL_CREATE);
        Log.i("database","db created ");
    }
//update the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TableItems.SQL_DELETE);
        onCreate(db);

    }
}
