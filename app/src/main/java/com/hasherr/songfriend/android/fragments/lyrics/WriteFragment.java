package com.hasherr.songfriend.android.fragments.lyrics;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.R;

/**
 * Created by Evan on 1/18/2016.
 */
public class WriteFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.layout_lyric, container, false);
    }
}

