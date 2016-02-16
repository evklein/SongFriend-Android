package com.hasherr.songfriend.android.fragments.lyrics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.R;

/**
 * Created by Evan on 1/18/2016.
 */
public class LyricMenuFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_lyric, container, false);
        return view;
    }
}

