package com.emad.cctv.resturantmenu.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.emad.cctv.resturantmenu.R;
import com.emad.cctv.resturantmenu.adapter.ListItemRvAdapter;
import com.emad.cctv.resturantmenu.database.ItemDataSource;
import com.emad.cctv.resturantmenu.model.DataItem;
import com.emad.cctv.resturantmenu.utils.JsonHelper;

import java.util.List;

import static com.emad.cctv.resturantmenu.ui.SigninActivity.EMAIL_KEY;

public class MainActivity extends AppCompatActivity {
    private static final int SIGNIN_REQUEST =1 ;
    public static final String FILE_NAME ="com.emad.cctv.resturantmenu.ui" ;
    private static final int REQUEST_PERMISSION_WRITE =101 ;
    private RecyclerView mRecyclerView;
    private List<DataItem> dataList;
    private boolean permissionGranted;
    private DrawerLayout mDrawerLayout;
    private ListView mListViewDrawer;
    private ItemDataSource mItemDataSource;
    private String [] mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        // initialize the drawer layout
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout) ;
        //get the categories string array
        mCategories=getResources().getStringArray(R.array.categories);
        //the list view will be inside the drawer and contain the list of Catergories
        mListViewDrawer =(ListView) findViewById(R.id.left_drawer);
        mListViewDrawer.setAdapter(new ArrayAdapter<>(this,R.layout.drawer_list_item,mCategories));

        mListViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // when selecting a category from the drawer list
                // we are getting all items with under the selected category
                dataList=mItemDataSource.getMenuItem(mCategories[position]);
                // then we are displaying the items
                displayItems();
                //then close the drawer
                mDrawerLayout.closeDrawer(mListViewDrawer);
            }
        });


        // please see the explanation in the DBHelper class
        mItemDataSource=new ItemDataSource(this);
        mItemDataSource.open();

        //getting the list of data from the data provider
        //dataList= SampleDataProvider.dataItemList;
        dataList=mItemDataSource.getMenuItem(null);
        long itemsCount=mItemDataSource.getItemCount();
        // if the database doesn't have any entries
        if (itemsCount==0){
            //adding the data from the list to the database
            for (DataItem item:dataList) {
                mItemDataSource.insertItem(item);
                Log.i("database",item.getItemName()+" has been added");
            }
        }
        displayItems();


    }

    private void displayItems() {
        //here we are getting the default shared Preferences
        SharedPreferences settingPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //then we have to set a listner to the changes happening of the values
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.i("Key", "onSharedPreferenceChanged" + key);
            }
        };
        //here we are registering the changes
        settingPreferences.registerOnSharedPreferenceChangeListener(listener);
        //by default the value of the check box will be saved there
        //so her we are getting it from the default value
        boolean grid = settingPreferences
                .getBoolean(getString(R.string.grid_view_option_key), false);

        //here we need to judge if listView or GridView
        if (grid){
            //set the grid layout if the check box value true
            mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        }else{
            //else set as Liner layout
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        ListItemRvAdapter adapter = new ListItemRvAdapter(this, dataList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signin:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
                return true;
            case R.id.action_Settings:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                return true;
            case R.id.export_json:
                /*to Export the menu information as Json file to the external storage we need fire to create a helper class
                create a utlis pakage and create a class we call it JsonHelper
                go to the class to see the details*/


                /*writting to external storage need permission
                we have already delcared the permission in the manifest file
                but starting from android 6 you need to ask the user the permission in the runtime not only during installation
                so we are creatint the below methods to check the permission andto grant the permission*/
                if (!permissionGranted){
                    checkPermissions();

                }else{
                    boolean result= JsonHelper.exportToJson(this,dataList);

                    if (result) {
                        Toast.makeText(this, "Succeed", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case R.id.import_json:
                List<DataItem> dataListItems= JsonHelper.importFromJson(this);
                if(dataListItems!=null) {
                    for (DataItem dataitem : dataListItems) {
                        Log.i("info", dataitem.getItemName());
                    }
                }
                return true;

            case R.id.filter:
                mDrawerLayout.openDrawer(mListViewDrawer);
                return true;
            case R.id.select_all:
                dataList=mItemDataSource.getMenuItem(null);
                displayItems();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(EMAIL_KEY);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor= getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
            editor.putString(EMAIL_KEY,email);
            editor.apply();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mItemDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mItemDataSource.open();
        displayItems();
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        //her we declare which permission we need to check
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // if the value of permission check dont equal permission granted
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // then we need to as the permission and we pass the permission name and the permission request number
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }
    // to handle the permission result and seve it in variable to check it every time
    //we override the below method and we set a boolean true if granted and false if not
    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        //here we are judging the request code
        switch (requestCode) {
            //our request code is the code that we difined to write to the external storage
            case REQUEST_PERMISSION_WRITE:
                //the grant result passed as an integer array to this method
                //it includes all the permissions requested from the user
                //since we are requesting only one permission then we need to check the first cell in the array
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



}
