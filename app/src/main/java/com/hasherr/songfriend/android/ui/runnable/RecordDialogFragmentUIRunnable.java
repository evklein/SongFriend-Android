package com.hasherr.songfriend.android.ui.runnable;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasherr.songfriend.android.audio.AudioTimerRunnable;
import com.hasherr.songfriend.android.ui.handler.BlinkerHandler;

/**
 * Created by Evan on 2/8/2016.
 */
public class RecordDialogFragmentUIRunnable implements Runnable
{
    private BlinkerHandler blinkerManager;
    private TextView durationView;
    private AudioTimerRunnable audioTimerRunnable;
    private Handler handler;

    public RecordDialogFragmentUIRunnable(ImageView blinkerView, TextView durationView, AudioTimerRunnable audioTimerRunnable)
    {
        blinkerManager = new BlinkerHandler(blinkerView);
        this.durationView = durationView;
        this.audioTimerRunnable = audioTimerRunnable;
        handler = new Handler();
    }

    @Override
    public void run()
    {
        audioTimerRunnable.run();
        blinkerManager.manageBlinker();
        durationView.setText(audioTimerRunnable.getCurrentTime());
        handler.postDelayed(this, 100);
    }

    public void restart()
    {
        audioTimerRunnable.restartTimer();
    }

    public void stop()
    {
        handler.removeCallbacks(this);
    }
}
