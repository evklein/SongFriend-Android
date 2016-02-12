package com.hasherr.songfriend.android.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.hasherr.songfriend.android.fragments.lyrics.WriteFragment;
import com.hasherr.songfriend.android.fragments.play.PerformFragment;
import com.hasherr.songfriend.android.fragments.record.RecordMenuFragment;

/**
 * Created by Evan on 1/18/2016.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter
{
    private String lyricPath;
    private String recordingPath;

    public CustomPagerAdapter(FragmentManager fm, String lyricPath, String recordingPath)
    {
        super(fm);
        this.lyricPath = lyricPath;
        this.recordingPath = recordingPath;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new WriteFragment();
            case 1:
                RecordMenuFragment recordMenuFragment = new RecordMenuFragment();
                Bundle recordingBundle = new Bundle();
                recordingBundle.putString("recordingPath", recordingPath);
                recordMenuFragment.setArguments(recordingBundle);
                return recordMenuFragment;
            case 2:
                return new PerformFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }
}
