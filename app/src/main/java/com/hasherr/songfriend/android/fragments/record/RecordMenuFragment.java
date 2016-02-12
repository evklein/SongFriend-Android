package com.hasherr.songfriend.android.fragments.record;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.adapter.AudioListArrayAdapter;
import com.hasherr.songfriend.android.ui.handler.DeleteDialogHandler;
import com.hasherr.songfriend.android.ui.handler.ListHandler;
import com.hasherr.songfriend.android.ui.listener.CustomOrientationListener;
import com.hasherr.songfriend.android.ui.listener.FloatingActionButtonListener;
import com.hasherr.songfriend.android.utility.FileUtilities;

/**
 * Created by Evan on 1/18/2016.
 */
public class RecordMenuFragment extends Fragment implements FloatingActionButtonListener, CustomOrientationListener
{
    private Activity parentActivity;
    private ListHandler listHandler;
    private String recordingPath;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.layout_record, container, false);
        recordingPath = getArguments().getString("recordingPath");
        setSpecifiedOrientation();
        initFloatingActionButton();

        listHandler = new ListHandler((ListView) view.findViewById(R.id.recordingListView),
                new AudioListArrayAdapter(parentActivity, FileUtilities.getSortedFilePathsReverse(recordingPath)), recordingPath);
        listHandler.initListViewItemClick(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                PlayDialogFragment playDialogFragment = getPlayDialogFragment((String) parent.getItemAtPosition(position));
                playDialogFragment.show(getFragmentManager(), "fragment_dialog_play");
            }
        });

        listHandler.initListViewLongItemClick(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                String itemToDelete = (String) parent.getItemAtPosition(position);
                DeleteDialogHandler deleteDialogHandler = new DeleteDialogHandler();
                deleteDialogHandler.initialize(getActivity(), "Delete Recording", "Are you sure you want to delete this recording?",
                        itemToDelete, recordingPath, false, listHandler);
                deleteDialogHandler.show();
                return true;
            }
        });

        return view;
    }

    private RecordDialogFragment getRecordDialogFragment()
    {
        RecordDialogFragment recordDialogFragment = new RecordDialogFragment();
        Bundle recordingInfo = new Bundle();
        recordingInfo.putString("recordingPath", recordingPath);
        recordDialogFragment.setArguments(recordingInfo);
        return recordDialogFragment;
    }

    private PlayDialogFragment getPlayDialogFragment(String path)
    {
        PlayDialogFragment playDialogFragment = new PlayDialogFragment();
        Bundle recordingInfo = new Bundle();
        recordingInfo.putString("recordingPath", path);
        playDialogFragment.setArguments(recordingInfo);
        return playDialogFragment;
    }

    @Override
    public void setSpecifiedOrientation()
    {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

    @Override
    public void initFloatingActionButton()
    {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addRecordingFloatingActionButton);
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.logoGreen));
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RecordDialogFragment recordDialogFragment = getRecordDialogFragment();
                recordDialogFragment.setTargetFragment(RecordMenuFragment.this, FileUtilities.MODIFY_REQUEST_CODE);
                recordDialogFragment.show(getFragmentManager(), "fragment_dialog_record");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        listHandler.refresh(FileUtilities.reverseArray(FileUtilities.getPathList(recordingPath)));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }
}
