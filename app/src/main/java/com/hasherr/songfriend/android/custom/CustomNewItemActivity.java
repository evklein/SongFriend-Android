package com.hasherr.songfriend.android.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.hasherr.songfriend.android.ui.listener.ErrorListener;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by evan on 1/11/16.
 */
public abstract class CustomNewItemActivity extends ToolBarActivity implements ErrorListener
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
        initPath();
        FileUtilities.createDirectory(elements, path);
        intentManager.makePathBundle(FileUtilities.DIRECTORY_TAG, path);
    }

    protected String getEditTextContents(int editTextID)
    {
        return ((EditText) findViewById(editTextID)).getText().toString().trim();
    }

    protected abstract void createElements();
}
