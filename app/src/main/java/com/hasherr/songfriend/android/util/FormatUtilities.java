package com.hasherr.songfriend.android.util;

/**
 * Created by Evan on 2/5/2016.
 */
public class FormatUtilities
{
    public static String getFormattedTime(int milliseconds)
    {
        int minutes = milliseconds / 60000;
        milliseconds -= minutes;
        int seconds = milliseconds / 1000;

        String formattedMinutes = Integer.toString(minutes);
        String formattedSeconds = Integer.toString(seconds);

        if (minutes < 10) formattedMinutes = "0" + minutes;
        if (seconds < 10) formattedSeconds = "0" + seconds;

        return formattedMinutes + ":" + formattedSeconds;
    }

    /* RecordDialogFragment Version */
    public static String getFormattedTime(int minutes, int remainingMilliseconds)
    {
        int seconds = remainingMilliseconds / 1000;

        String formattedMinutes = Integer.toString(minutes);
        String formattedSeconds = Integer.toString(seconds);

        if (minutes < 10) formattedMinutes = "0" + minutes;
        if (seconds < 10) formattedSeconds = "0" + seconds;

        // The spacing here is inconsistent because this format is specifically designated to the RecordDialogFragment.
        return formattedMinutes + " : " + formattedSeconds;
    }

    public static String getFormattedDirectoryTitle(String filePath)
    {
        int lastLevelIndex = 0;
        for (int i = 0; i < filePath.length(); i++)
        {
            if (filePath.charAt(i) == '/')
                lastLevelIndex = i;
        }
        return filePath.substring(lastLevelIndex + 1);
    }

    public static String getFormattedFileTitle(String filePath, String fileExtension)
    {
        int lastLevelIndex = 0;
        for (int i = 0; i < filePath.length(); i++)
        {
            if (filePath.charAt(i) == '/')
            {
                lastLevelIndex = i;
            }
        }

        return filePath.substring(lastLevelIndex + 1, filePath.indexOf("." + fileExtension));
    }
}
