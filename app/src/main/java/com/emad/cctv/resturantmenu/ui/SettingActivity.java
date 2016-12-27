package com.emad.cctv.resturantmenu.ui;

import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emad.cctv.resturantmenu.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //here you will get the fragment and add it to your layout
        getFragmentManager().beginTransaction()
                .add(R.id.activity_setting,new SettingFragment())
                .commit();

    }
    // after creating the Activity then you need to create an inner class
    //which exents the PreferenceFragment
    public static class SettingFragment extends PreferenceFragment {
        //we override the onCreate Method
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }
}
