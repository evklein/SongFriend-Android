package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hasherr.songfriend.android.custom.FloatingActionButtonListener;
import com.hasherr.songfriend.android.custom.activity.CustomMenuActivity;
import com.hasherr.songfriend.android.custom.delegate.DeleteDialogHandler;
import com.hasherr.songfriend.android.custom.delegate.ListHandler;
import com.hasherr.songfriend.android.util.FileUtilities;

public class OpenProjectActivity extends CustomMenuActivity implements FloatingActionButtonListener
{
    private ListHandler listHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_project);
        initToolbar(R.id.openProjectToolbar, "Open a project");
        initFloatingActionButton();

        listHandler = new ListHandler((ListView) findViewById(R.id.projectListView),
                new ArrayAdapter<>(this, R.layout.row, FileUtilities.getDirectoryList(path)), path);

        listHandler.initListViewItemClick(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String elementName = ((String) parent.getItemAtPosition(position));
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path + "/" + elementName);
                startActivity(intentManager.createIntent(OpenProjectActivity.this, OpenDraftActivity.class));
            }
        });

        listHandler.initListViewLongItemClick(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final String itemToDelete = (String) parent.getItemAtPosition(position);
                DeleteDialogHandler deleteDialogHandler = new DeleteDialogHandler();
                deleteDialogHandler.initialize(OpenProjectActivity.this, "Delete Project", "Are you sure you want to delete this project?",
                        path + "/" + itemToDelete, path, listHandler);
                deleteDialogHandler.show();
                return true;
            }
        });
    }

    @Override
    protected void initValues()
    {
        createPath();
    }

    @Override
    protected void createPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY;
    }

    @Override
    public void initFloatingActionButton()
    {
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.projectFloatingActionButton);
        floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.logoGreen));
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, FileUtilities.PROJECT_DIRECTORY);
                startActivity(intentManager.createIntent(OpenProjectActivity.this, NewProjectActivity.class));
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
