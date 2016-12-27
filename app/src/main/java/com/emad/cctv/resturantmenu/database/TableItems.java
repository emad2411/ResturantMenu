package com.emad.cctv.resturantmenu.database;

/*in this class we are creating a static final variables of the quires Table Name and columns names
* all the variables must be a static so we don't need to creat an instance of the class in the DBHelper class
* */
public class TableItems {

        public static final String TABLE_ITEMS = "items";
        public static final String COLUMN_ID = "itemId";
        public static final String COLUMN_NAME = "itemName";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_POSITION = "sortPosition";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_ITEMS + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_CATEGORY + " TEXT," +
                        COLUMN_POSITION + " INTEGER," +
                        COLUMN_PRICE + " REAL," +
                        COLUMN_IMAGE + " TEXT" + ");";
        public static final String SQL_DELETE =
                "DROP TABLE " + TABLE_ITEMS;


}
