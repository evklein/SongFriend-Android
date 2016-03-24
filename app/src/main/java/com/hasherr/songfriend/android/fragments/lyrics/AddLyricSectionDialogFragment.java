package com.hasherr.songfriend.android.fragments.lyrics;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hasherr.songfriend.android.R;

/**
 * Created by evan on 3/24/16.
 */
public class AddLyricSectionDialogFragment extends DialogFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.FragmentDialogFragmentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_dialog_lyric_section_add, container, false);
        return view;
    }

}
