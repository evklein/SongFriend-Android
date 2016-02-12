package com.hasherr.songfriend.android;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.hasherr.songfriend.android.custom.CustomNewItemActivity;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.IOException;
import java.util.ArrayList;

public class NewDraftActivity extends CustomNewItemActivity
{
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_draft);
        initToolbar(R.id.newDraftToolbar, "New Draft - " + projectName);
        initSpinner();
    }

    @Override
    protected void createElements()
    {
        elements.put("title", getEditTextContents(R.id.draftTitleEditText));
        elements.put("description", getEditTextContents(R.id.draftDescriptionEditText));
    }

    @Override
    public void createPath()
    {
        path = FileUtilities.PROJECT_DIRECTORY + "/" + projectName + "/" + getEditTextContents(R.id.draftTitleEditText);
    }

    public void createDraft(View view) throws IOException
    {
        if (!hasErrors(R.id.newDraftErrorTextView, R.id.draftTitleEditText,
                FileUtilities.PROJECT_DIRECTORY + "/" + projectName, "draft"))
        {
            super.createItem(view);
            startActivity(intentManager.createIntent(this, ProjectActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) // Handles the back arrow on the Toolbar.
    {
        finish(); 
        return true;
    }

    @Override
    protected void initValues()
    {
        projectName = FileUtilities.getDirectoryAtLevel(getIntent().getExtras().getString(FileUtilities.DIRECTORY_TAG),
                FileUtilities.PROJECT_LEVEL);
    }

    private void initSpinner()
    {
        Spinner draftSpinner = (Spinner) findViewById(R.id.draftSpinner);
        draftSpinner.getBackground().setColorFilter(getResources().getColor(R.color.logoGreen),
                PorterDuff.Mode.SRC_ATOP); // Sets spinner arrow icon to green
        ArrayList<String> draftArrayList = FileUtilities.getDirectoryList(
                FileUtilities.PROJECT_DIRECTORY + "/" + projectName);
        draftArrayList.add(0, "New Draft");
        ArrayAdapter<String> draftArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_element, draftArrayList);
        draftSpinner.setAdapter(draftArrayAdapter);
    }
}
