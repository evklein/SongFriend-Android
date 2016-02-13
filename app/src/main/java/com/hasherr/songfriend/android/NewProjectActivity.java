package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.hasherr.songfriend.android.custom.CustomNewItemActivity;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.IOException;
import java.util.ArrayList;

public class NewProjectActivity extends CustomNewItemActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        initToolbar(R.id.newProjectToolbar, "Start a new project");
        initPath();
    }

    public void createProject(View view) throws IOException
    {
        if (!hasErrors())
        {
            super.createItem(view);
            startActivity(intentManager.createIntent(this, OpenDraftActivity.class));
        }
    }

    @Override
    public boolean hasErrors()
    {
        TextView errorTextView = (TextView) findViewById(R.id.projectTitleEditText);
        String title = getEditTextContents(R.id.projectTitleEditText).toLowerCase();

        if (title.equals(""))
        {
            errorTextView.setText("Please add a title.");
            return true;
        }

        ArrayList<String> allItemNames = FileUtilities.getDirectoryList(path);
        for (String s : allItemNames)
        {
            if (s.toLowerCase().equals(title))
            {
                errorTextView.setText("That project already exists.");
                return true;
            }
        }

        return false;
    }

    @Override
    public void initPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY + "/" + getEditTextContents(R.id.projectTitleEditText);
    }

    @Override
    protected void initValues() { }

    @Override
    protected void createElements()
    {
        elements.put("title", getEditTextContents(R.id.projectTitleEditText));
        elements.put("genre", getEditTextContents(R.id.projectGenreEditText));
        elements.put("description", getEditTextContents(R.id.projectDescriptionEditText));
        elements.put("tools", getEditTextContents(R.id.projectToolsEditText));
    }
}
