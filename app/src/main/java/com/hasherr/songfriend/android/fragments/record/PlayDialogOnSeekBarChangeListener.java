package com.hasherr.songfriend.android.fragments.record;

import android.media.MediaPlayer;
import android.widget.SeekBar;

/**
 * Created by Evan on 2/12/2016.
 */
public class PlayDialogOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener
{
    private MediaPlayer mediaPlayer;

    public PlayDialogOnSeekBarChangeListener(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if (mediaPlayer != null && fromUser)
        {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
}
