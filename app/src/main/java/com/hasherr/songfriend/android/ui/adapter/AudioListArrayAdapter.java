package com.hasherr.songfriend.android.ui.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.utility.FormatUtilities;

import java.util.ArrayList;

/**
 * Created by Evan on 2/4/2016.
 */
public class AudioListArrayAdapter extends ArrayAdapter<String>
{
    private Activity parentActivity;
    private ArrayList<String> filePaths;

    public AudioListArrayAdapter(Activity parentActivity, ArrayList<String> filePaths)
    {
        super(parentActivity, R.layout.recording_row, filePaths);
        this.parentActivity = parentActivity;
        this.filePaths = filePaths;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater layoutInflater = parentActivity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.recording_row, null, true);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.rowTitles);
        TextView durationTextView = (TextView) rowView.findViewById(R.id.rowDurations);

        String currentPath = filePaths.get(position);
        titleTextView.setText(FormatUtilities.getFormattedFileTitle(currentPath, "wav"));
        MediaPlayer tempMediaPlayer = MediaPlayer.create(parentActivity, Uri.parse(currentPath));
        durationTextView.setText(FormatUtilities.getFormattedTime(tempMediaPlayer.getDuration()));
        return rowView;
    }

}
