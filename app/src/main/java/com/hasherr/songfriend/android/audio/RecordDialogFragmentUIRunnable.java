package com.hasherr.songfriend.android.audio;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.ui.handler.BlinkerHandler;

/**
 * Created by Evan on 2/8/2016.
 */
public class RecordDialogFragmentUIRunnable implements Runnable
{
    private View view;
    private BlinkerHandler blinkerManager;
    private TextView durationTextView;
    private AudioTimerRunnable audioTimerRunnable;
    private Handler handler;

    public RecordDialogFragmentUIRunnable(View view, AudioTimerRunnable audioTimerRunnable)
    {
        this.view = view;
        blinkerManager = new BlinkerHandler((ImageView) view.findViewById(R.id.blinkerView));
        durationTextView = (TextView) view.findViewById(R.id.recordDialogLengthTextView);
        this.audioTimerRunnable = audioTimerRunnable;
        handler = new Handler();
    }

    @Override
    public void run()
    {
        audioTimerRunnable.run();
        blinkerManager.manageBlinker();
        durationTextView.setText(audioTimerRunnable.getCurrentTime());
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
