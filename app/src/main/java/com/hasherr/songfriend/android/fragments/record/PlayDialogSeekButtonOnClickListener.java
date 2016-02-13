package com.hasherr.songfriend.android.fragments.record;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Evan on 2/12/2016.
 */
public class PlayDialogSeekButtonOnClickListener implements View.OnClickListener
{
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private int interval;
    private int extreme;

    public PlayDialogSeekButtonOnClickListener(MediaPlayer mediaPlayer, SeekBar seekBar, int interval, int extreme)
    {
        this.mediaPlayer = mediaPlayer;
        this.seekBar = seekBar;
        this.interval = interval;
        this.extreme = extreme;
    }

    @Override
    public void onClick(View v)
    {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + interval);
        if (mediaPlayer.getCurrentPosition() < 0 || mediaPlayer.getCurrentPosition() > mediaPlayer.getDuration())
            mediaPlayer.seekTo(extreme);
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
    }
}
