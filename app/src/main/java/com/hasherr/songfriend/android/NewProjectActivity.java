package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.view.View;
import com.hasherr.songfriend.android.custom.activity.CustomNewItemActivity;
import com.hasherr.songfriend.android.util.FileUtilities;

import java.io.IOException;

public class NewProjectActivity extends CustomNewItemActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        initToolbar(R.id.newProjectToolbar, "Start a new project");
    }

    @Override
    protected void createElements()
    {
        elements.put("title", getEditTextContents(R.id.projectTitleEditText));
        elements.put("genre", getEditTextContents(R.id.projectGenreEditText));
        elements.put("description", getEditTextContents(R.id.projectDescriptionEditText));
        elements.put("tools", getEditTextContents(R.id.projectToolsEditText));
    }

    @Override
    protected void createPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY + "/" + getEditTextContents(R.id.projectTitleEditText);
    }

    public void createProject(View view) throws IOException
    {
        if (!hasErrors(R.id.newProjectErrorTextView, R.id.projectTitleEditText, FileUtilities.PROJECT_DIRECTORY,
                "project"))
        {
            super.createItem(view);
            startActivity(intentManager.createIntent(this, OpenDraftActivity.class));
        }
    }

    @Deprecated
    protected void initValues() { }
}
