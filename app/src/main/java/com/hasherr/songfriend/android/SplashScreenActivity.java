package com.hasherr.songfriend.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import com.hasherr.songfriend.android.ui.visual.WindowSlideInAnimator;
import com.hasherr.songfriend.android.utility.AudioUtilities;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.File;

public class SplashScreenActivity extends AppCompatActivity
{
    private WindowSlideInAnimator windowAnimator;
    private final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        initNecessaryDirectories();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        windowAnimator = new WindowSlideInAnimator();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(SplashScreenActivity.this, OpenProjectActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DELAY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        ViewGroup windowRoot = (ViewGroup) findViewById(android.R.id.content);
        windowAnimator.animate(windowRoot, hasFocus, WindowSlideInAnimator.SLIDE_BOTTOM);
    }

    private void initNecessaryDirectories()
    {
        FileUtilities.createDirectory(FileUtilities.PROJECT_DIRECTORY);
        FileUtilities.delete(new File(AudioUtilities.TEMP_AUDIO_PATH));
    }
}
