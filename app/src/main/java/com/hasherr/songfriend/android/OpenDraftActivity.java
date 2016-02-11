package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hasherr.songfriend.android.ui.listener.FloatingActionButtonListener;
import com.hasherr.songfriend.android.custom.CustomMenuActivity;
import com.hasherr.songfriend.android.ui.handler.DeleteDialogHandler;
import com.hasherr.songfriend.android.ui.handler.ListHandler;
import com.hasherr.songfriend.android.utility.FileUtilities;

public class OpenDraftActivity extends CustomMenuActivity implements FloatingActionButtonListener
{
    private String projectName;
    private ListHandler listHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_draft);
        initToolbar(R.id.openDraftToolbar, "Open a draft - " + projectName);
        initFloatingActionButton();

        listHandler = new ListHandler((ListView) findViewById(R.id.draftListView),
                new ArrayAdapter<>(OpenDraftActivity.this, R.layout.row, FileUtilities.getDirectoryList(path)), path);

        listHandler.initListViewItemClick(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String elementName = ((String) parent.getItemAtPosition(position));
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path + "/" + elementName);
                startActivity(intentManager.createIntent(OpenDraftActivity.this, ProjectActivity.class));
            }
        });

        listHandler.initListViewLongItemClick(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final String itemToDelete = (String) parent.getItemAtPosition(position);
                DeleteDialogHandler deleteDialogHandler = new DeleteDialogHandler();
                deleteDialogHandler.initialize(OpenDraftActivity.this, "Delete Draft",
                        "Are you sure you want to delete this draft?", path + "/" + itemToDelete, path, listHandler);
                deleteDialogHandler.show();
                return true;
            }
        });
    }

    @Override
    protected void initValues()
    {
        projectName = FileUtilities.getDirectoryAtLevel(getIntent().getExtras().getString(FileUtilities.DIRECTORY_TAG),
                FileUtilities.PROJECT_LEVEL);
        createPath();
    }

    @Override
    protected void createPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY + "/" + projectName;
    }

    @Override
    public void initFloatingActionButton()
    {
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(
                R.id.openDraftFloatingActionBar);
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.logoGreen));
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path);
                startActivity(intentManager.createIntent(OpenDraftActivity.this, NewDraftActivity.class));
            }
        });
    }

    @Override
    public void onResume()
    {
        listHandler.refresh(FileUtilities.getDirectoryList(path));
        super.onResume();
    }
}
