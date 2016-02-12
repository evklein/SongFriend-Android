package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.ui.visual.WindowAnimator;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.File;


public class MainActivity extends AppCompatActivity
{
    private WindowAnimator windowAnimator;
    private IntentManager intentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);
        makeBaseProjectDirectory();
        windowAnimator = new WindowAnimator();
        intentManager = new IntentManager();
    }

    public void startProject(View view)
    {
        startActivity(intentManager.createIntent(this, NewProjectActivity.class));
    }

    public void openProject(View view)
    {
        startActivity(intentManager.createIntent(this, OpenProjectActivity.class));
    }

    public void openAboutPage(View view)
    {

    }

    private void makeBaseProjectDirectory()
    {
        FileUtilities.createDirectory(FileUtilities.PROJECT_DIRECTORY);
        FileUtilities.delete(new File(FileUtilities.PROJECT_DIRECTORY + "/Temps"));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup windowRoot = (ViewGroup) findViewById(android.R.id.content);
        windowAnimator.animate(windowRoot, hasFocus);
    }
}