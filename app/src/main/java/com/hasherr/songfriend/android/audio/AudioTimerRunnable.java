package com.hasherr.songfriend.android.audio;

import android.os.Handler;
import com.hasherr.songfriend.android.util.FormatUtilities;

/**
 * Created by evan on 1/20/16.
 */
public class AudioTimerRunnable implements Runnable
{
    private Handler handler;
    private long start;
    private long minutes;
    private long milliseconds;
    private long oldMilliseconds;

    public AudioTimerRunnable(Handler handler)
    {
        this.handler = handler;
        start = System.currentTimeMillis();
        minutes = 0L;
        oldMilliseconds = 0L;
    }

    @Override
    public void run()
    {
        getCurrentTime();
    }

    public String getCurrentTime()
    {
        milliseconds = System.currentTimeMillis() - start;
        milliseconds += oldMilliseconds;
        updateMinutes();
        return FormatUtilities.getFormattedTime((int) minutes, (int) milliseconds);
    }

    private void updateMinutes()
    {
        if (milliseconds > 59999)
        {
            minutes++;
            start = System.currentTimeMillis();
            milliseconds = 0;
            oldMilliseconds = 0;
        }
    }

    public void restartTimer()
    {
        start = System.currentTimeMillis();
        oldMilliseconds = milliseconds;
    }
}
