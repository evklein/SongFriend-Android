package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.project.IntentManager;
import com.hasherr.songfriend.android.ui.visual.WindowSlideInAnimator;
import com.hasherr.songfriend.android.utility.AudioUtilities;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.File;


public class MainActivity extends AppCompatActivity
{
    private WindowSlideInAnimator windowAnimator;
    private IntentManager intentManager;
    private static boolean hasAnimated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);
        initNecessaryDirectories();
        windowAnimator = new WindowSlideInAnimator();
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (!hasAnimated)
        {
            ViewGroup windowRoot = (ViewGroup) findViewById(android.R.id.content);
            windowAnimator.animate(windowRoot, hasFocus);
            hasAnimated = true;
        }
    }

    private void initNecessaryDirectories()
    {
        FileUtilities.createDirectory(FileUtilities.PROJECT_DIRECTORY);
        FileUtilities.delete(new File(AudioUtilities.TEMP_AUDIO_PATH));
    }
}