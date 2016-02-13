package com.hasherr.songfriend.android.fragments.record;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.project.Killable;
import com.hasherr.songfriend.android.project.PathListener;
import com.hasherr.songfriend.android.ui.listener.CustomOrientationListener;

import java.io.IOException;

/**
 * Created by evan on 2/1/16.
 */
public class PlayDialogFragment extends DialogFragment implements MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl, Killable, CustomOrientationListener, PathListener
{
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable seekBarProgressRunnable;
    private String recordingPath;
    private boolean isPlaying;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.FragmentDialogFragmentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_dialog_play, container, false);
        handler = new Handler();
        isPlaying = false;
        initPath();
        setRetainInstance(true);
        setSpecifiedOrientation();
        initMediaPlayer();
        initTitle(view);
        initAudioSeeker(view);
        initPlayPauseButton(view);
        initExitButton(view);
        initSeekButtons(view);
        return view;
    }

    @Override
    public void start()
    {
        handler.postDelayed(seekBarProgressRunnable, 50);
        mediaPlayer.start();
    }

    @Override
    public void pause()
    {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration()
    {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition()
    {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos)
    {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() { return 0; }

    @Override
    public boolean canPause() { return true; }

    @Override
    public boolean canSeekBackward() { return true; }

    @Override
    public boolean canSeekForward() { return true; }

    @Override
    public int getAudioSessionId() { return mediaPlayer.getAudioSessionId(); }

    @Override
    public void onPrepared(MediaPlayer mp) {}

    @Override
    public void initPath()
    {
        recordingPath = getArguments().getString("recordingPath");
    }

    @Override
    public void setSpecifiedOrientation()
    {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), getTheme())
        {
            @Override
            public void onBackPressed()
            {
                PlayDialogFragment.this.dismiss();
            }
        };
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    private void initMediaPlayer()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setOnPreparedListener(this);
        try
        {
            mediaPlayer.setDataSource(recordingPath);
            mediaPlayer.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss()
    {
        kill();
        super.dismiss();
    }

    @Override
    public void kill()
    {
        handler.post(null);
        isPlaying = false;
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

    private void initTitle(View view)
    {
        TextView titleTextView = (TextView) view.findViewById(R.id.playDialogTextView);
        StringBuilder builder = new StringBuilder(getArguments().getString("recordingPath"));
        String reversedTitle = builder.reverse().toString();
        String reversedTitleRemovedClutter = reversedTitle.substring(reversedTitle.indexOf(".") + 1, reversedTitle.indexOf("/"));
        titleTextView.setText(new StringBuilder(reversedTitleRemovedClutter).reverse().toString());
    }

    private void initAudioSeeker(View view)
    {
        final SeekBar audioSeekBar = (SeekBar) view.findViewById(R.id.playSeekBar);
        audioSeekBar.setMax(getDuration());
        audioSeekBar.setOnSeekBarChangeListener(new PlayDialogOnSeekBarChangeListener(mediaPlayer));
        seekBarProgressRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (isPlaying)
                        {
                            audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                            handler.postDelayed(this, 50);
                        }
                    }
                });
            }
        };
    }

    private void initPlayPauseButton(View view)
    {
        final Button playPauseButton = (Button) view.findViewById(R.id.pausePlayButton);
        playPauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!isPlaying)
                {
                    playPauseButton.setBackgroundResource(R.drawable.pause_button);
                    isPlaying = true;
                    start();
                }
                else
                {
                    playPauseButton.setBackgroundResource(R.drawable.play_button);
                    isPlaying = false;
                    pause();
                }
            }
        });
    }

    private void initSeekButtons(View view)
    {
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.playSeekBar);
        Button seekBackButton = (Button) view.findViewById(R.id.seekBackButton);
        Button seekForwardButton = (Button) view.findViewById(R.id.seekForwardButton);
        seekBackButton.setOnClickListener(new PlayDialogSeekButtonOnClickListener(mediaPlayer, seekBar, -5000, 0));
        seekForwardButton.setOnClickListener(new PlayDialogSeekButtonOnClickListener(mediaPlayer, seekBar, 5000, getDuration()));
    }

    private void initExitButton(View view)
    {
        Button exitButton = (Button) view.findViewById(R.id.exitPlayDialogButton);
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }
}
