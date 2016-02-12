package com.hasherr.songfriend.android.audio;

import com.hasherr.songfriend.android.utility.FormatUtilities;

/**
 * Created by evan on 1/20/16.
 */
public class AudioTimerRunnable implements Runnable
{
    private long start, minutes, milliseconds, oldMilliseconds;

    public AudioTimerRunnable()
    {
        start = System.currentTimeMillis();
        minutes = 0L;
        milliseconds = 0L;
        oldMilliseconds = 0L;
    }

    public void restartTimer()
    {
        start = System.currentTimeMillis();
        oldMilliseconds = milliseconds;
    }

    public String getCurrentTime()
    {
        calculateMilliseconds();
        updateMinutes();
        return FormatUtilities.getFormattedTime((int) minutes, (int) milliseconds);
    }

    @Override
    public void run()
    {
        getCurrentTime();
    }

    private void calculateMilliseconds()
    {
        milliseconds = System.currentTimeMillis() - start;
        milliseconds += oldMilliseconds;
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
}
