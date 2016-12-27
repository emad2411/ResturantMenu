package com.emad.cctv.resturantmenu.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.emad.cctv.resturantmenu.model.DataItem;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

// since we need to export and import files from our app to the external storage
//then we need to creat a helper class which will help us to conver our list of menu items to json file
//and export it to the external storage

public class JsonHelper {

      //her we are creating finl string as a file name
    private static final String FILE_NAME="menuitems.json";

// in this class we have to creat to helper methods

    // one is for exporting the file after converting it to json fromat
    public static boolean exportToJson(Context context,List<DataItem> menuList){

        // to covert the values of List <DataItem> from and to Json format we need to create an inner model class
        //for the List<Dataitem> with setters and getters
        DataItems dataItems=new DataItems(menuList);
        // to convert a list<DataItem> to the json file we need to use GSON library from google
        //to use a GSON library we need to add the dependency in the build.gradle
        //the n we initialize GSON
        Gson gson=new Gson();
        //then we convert the list to json string by using the following method
        String jsonString=gson.toJson(dataItems);
        //now we have the json string we need to save it to the file
        // we creat file object and refer it to external storage directory  with the file name
        File file =new File(Environment.getExternalStorageDirectory(),FILE_NAME);
        // the following code is to exporting to the file by using FileOutputStream class
        FileOutputStream outputStream= null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(jsonString.getBytes());
            // returning tru if the operation successful
            return true;
        } catch (IOException e) {
            Log.e("error",e.getMessage());
        } finally {
            try {
                //finally close the outputStream
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("error",e.getMessage());
            }
        }
        return false;
    }


    // the other method is to import the file from external storage and convert the information from Json format
    // to list of data item
    public static List<DataItem> importFromJson(Context context){
        // to import a file we need to create a file object and refer to external storage
        File file =new File(Environment.getExternalStorageDirectory(),FILE_NAME);
        //then use file reader class to read the file
        FileReader reader= null;
        try {
            reader = new FileReader(file);
            //after reading the file we need to creatr a list<DataItems>
            Gson gson=new Gson();
            //the follwing method is reading from the file
            //we should pass the model class of the data type that we want to convert to
            //i n our case is the inner class of List<DataItem>
            DataItems dataItems=gson.fromJson(reader,DataItems.class);
            // here we are returning the value to the main activity
            return dataItems.getItemList();
        } catch (FileNotFoundException e) {
            Log.e("error",e.getMessage());
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("error",e.getMessage());
                }
            }
        }
// if there was a problem this method will return null
        return null;
    }


    // to covert the values of List <DataItem> from and to Json format we need to creat an inner model class
    //for the Lst<Dataitem> with setteres and getteres

    public static class DataItems{
        List<DataItem> itemList;

        public DataItems(List<DataItem> itemList) {
            this.itemList = itemList;
        }

        public List<DataItem> getItemList() {
            return itemList;
        }

        public void setItemList(List<DataItem> itemList) {
            this.itemList = itemList;
        }
    }


}
