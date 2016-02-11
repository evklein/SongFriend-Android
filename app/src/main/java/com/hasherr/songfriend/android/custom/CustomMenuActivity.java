package com.hasherr.songfriend.android.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.hasherr.songfriend.android.project.IntentManager;

/**
 * Created by evan on 1/8/16.
 */
public abstract class CustomMenuActivity extends AppCompatActivity
{
    protected String path;
    protected IntentManager intentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        intentManager = new IntentManager();
        initValues();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    protected abstract void initValues();
    protected abstract void createPath();

    protected void initToolbar(int toolbarID, String toolbarMessage)
    {
        Toolbar toolbar = (Toolbar) findViewById(toolbarID);
        toolbar.setTitle(toolbarMessage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
