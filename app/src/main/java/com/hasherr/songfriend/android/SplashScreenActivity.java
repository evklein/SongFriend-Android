package com.hasherr.songfriend.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.ui.visual.WindowSlideInAnimator;

public class SplashScreenActivity extends AppCompatActivity
{
    private WindowSlideInAnimator windowAnimator;
    private final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        windowAnimator = new WindowSlideInAnimator();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);

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
        windowAnimator.animate(windowRoot, hasFocus);
    }

}
