package com.hasherr.songfriend.android.utility;

import android.media.AudioFormat;

/**
 * Created by evan on 2/12/16.
 */
public class AudioUtilities
{
    public static final String TEMP_AUDIO_PATH = FileUtilities.PROJECT_DIRECTORY + "/Temps";
    public static final int SAMPLE_RATE = 44100;
    public static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    public static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    public static boolean isEmpty(byte[] audioData)
    {
        boolean isEmpty = true;
        for (byte b : audioData)
        {
            if (b != 0)
                isEmpty = false;
        }
        return isEmpty;
    }

    public static byte[] intToByteArray(int val)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (val & 0x00ff);
        bytes[1] = (byte) ((val >> 8) & 0x000000ff);
        bytes[2] = (byte) ((val >> 16) & 0x000000ff);
        bytes[3] = (byte) ((val >> 24) & 0x000000ff);
        return bytes;
    }

    public static byte[] shortToByteArray(short val)
    {
        byte[] bytes = new byte[]
                {
                        (byte)(val & 0xff),
                        (byte)((val >>> 8) & 0xff)
                };
        return bytes;
    }
}
