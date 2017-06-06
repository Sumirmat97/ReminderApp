package com.dummies.tasks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dummies.tasks.fragment.PreferencesFragment;

/**
 * Created by Sumir on 06-06-2017.
 */

public class PreferencesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
    }
}
