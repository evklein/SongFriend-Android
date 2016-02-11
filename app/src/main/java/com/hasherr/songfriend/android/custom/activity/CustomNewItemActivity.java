package com.hasherr.songfriend.android.custom.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.hasherr.songfriend.android.util.FileUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by evan on 1/11/16.
 */
public abstract class CustomNewItemActivity extends CustomMenuActivity
{
    protected Hashtable<String, String> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        elements = new Hashtable<>();
    }

    public void createItem(View view) throws IOException
    {
        createElements();
        createPath();
        FileUtilities.createDirectory(elements, path);
        intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path);
    }

    protected String getEditTextContents(int editTextID)
    {
        return ((EditText) findViewById(editTextID)).getText().toString().trim();
    }

    protected boolean hasErrors(int errorTextViewID, int titleEditTextID, String fileListDirectoryPath, String type)
    {
        TextView errorTextView = (TextView) findViewById(errorTextViewID);
        String title = getEditTextContents(titleEditTextID).toLowerCase();

        if (title.equals(""))
        {
            errorTextView.setText("Please add a title.");
            return true;
        }

        ArrayList<String> allItemNames = FileUtilities.getDirectoryList(fileListDirectoryPath);
        for (String s : allItemNames)
        {
            if (s.toLowerCase().equals(title))
            {
                errorTextView.setText("That " + type + " already exists.");
                return true;
            }
        }

        return false;
    }

    protected abstract void createElements();
}
