package com.hasherr.songfriend.android.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by evan on 1/8/16.
 */
public class IntentManager
{
    private Bundle extrasBundle;
    private Intent intent;

    public IntentManager()
    {
        extrasBundle = new Bundle();
    }

    public void makePathBundle(String key, String filePath)
    {
        extrasBundle.putString(key, filePath);
    }

    public Intent createIntent(Context initContext, Class newClass)
    {
        intent = new Intent(initContext, newClass);
        intent.putExtras(extrasBundle);
        return intent;
    }
}
