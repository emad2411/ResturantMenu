package com.emad.cctv.resturantmenu.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.emad.cctv.resturantmenu.model.DataItem;
import java.util.ArrayList;
import java.util.List;

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

    public long getItemCount(){
        // return the number of entres in the database
        return DatabaseUtils.queryNumEntries(mSQLiteDatabase,TableItems.TABLE_ITEMS);
    }
    //this method is to insert an item to the database
    public DataItem insertItem(DataItem item){
        /*to insert the an item in the data base we have to user Content values class to put all
        variables and then pass it to the database table */
        //in the item model class we create a method to insert all item variables in a ContentValues object
        ContentValues values=item.toValues();
        //after that we use the following method to add the ContentValues object to the table
        mSQLiteDatabase.insert(TableItems.TABLE_ITEMS,null,values);
        return item;
    }


    // this mehtod return an item valur from the table
    public List<DataItem> getMenuItem(String category){
        //we are passing a category string  it will help to filter based on the category

        List<DataItem> items=new ArrayList<>();

        Cursor cursor=null;

        if (category==null){
            // the cursor  is crossing all the table
            cursor=mSQLiteDatabase.query(TableItems.TABLE_ITEMS,
                    TableItems.TableColumns,null,null,null,null,TableItems.COLUMN_NAME);
            //last argument is for sorting
        }else{
            String[] categories={category};
            cursor=mSQLiteDatabase.query(TableItems.TABLE_ITEMS,
                    TableItems.TableColumns,TableItems.COLUMN_CATEGORY+"=?",categories,null,null,TableItems.COLUMN_NAME);
            //third and fourth argument is for filtering and getting the row data based on category pass in our case
        }
    //cursor is moving to the required row and getting the item data
        while (cursor.moveToNext()) {
                DataItem item = new DataItem();
                item.setItemId(cursor.getString(
                        cursor.getColumnIndex(TableItems.COLUMN_ID)));
                item.setItemName(cursor.getString(
                        cursor.getColumnIndex(TableItems.COLUMN_NAME)));
                item.setDescription(cursor.getString(
                        cursor.getColumnIndex(TableItems.COLUMN_DESCRIPTION)));
                item.setCategory(cursor.getString(
                        cursor.getColumnIndex(TableItems.COLUMN_CATEGORY)));
                item.setSortPosition(cursor.getInt(
                        cursor.getColumnIndex(TableItems.COLUMN_ID)));
                item.setPrice(cursor.getDouble(
                        cursor.getColumnIndex(TableItems.COLUMN_PRICE)));
                item.setPhoto(cursor.getString(
                        cursor.getColumnIndex(TableItems.COLUMN_IMAGE)));
                items.add(item);
            }
        //finally we have to close the cursor
        cursor.close();

        return items;
    }


}
