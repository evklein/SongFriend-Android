package com.hasherr.songfriend.android.ui.handler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.File;

/**
 * Created by Evan on 2/6/2016.
 */
public class DeleteDialogHandler
{
    private Context context;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String pathOfItemToDelete;
    private String basePath;
    private ListHandler listHandler;

    public void initialize(Context context, String title, String message, String basePath, String pathOfItemToDelete, ListHandler listHandler)
    {
        this.context = context;
        this.pathOfItemToDelete = pathOfItemToDelete;
        this.basePath = basePath;
        this.listHandler = listHandler;
        dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Theme_Delete));
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        setPositiveButton();
        setNegativeButton();
    }

    public void show()
    {
        dialog = dialogBuilder.show();
        setDividerColor(R.color.logoGreen);
    }

    private void setPositiveButton()
    {
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                FileUtilities.delete(new File(pathOfItemToDelete));
                listHandler.refresh(FileUtilities.getFileList(basePath));
                dialog.cancel();
            }
        });
    }

    private void setNegativeButton()
    {
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
    }

    private void setDividerColor(int colorID)
    {
        int titleDividerId = context.getResources().getIdentifier("titleDivider", "id", "android");
        View titleDivider = dialog.findViewById(titleDividerId);
        if (titleDivider != null) titleDivider.setBackgroundColor(context.getResources().getColor(colorID));
    }
}
