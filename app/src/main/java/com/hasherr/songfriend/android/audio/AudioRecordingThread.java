package com.hasherr.songfriend.android.audio;

import android.media.AudioRecord;

import java.io.*;

/**
 * Created by evan on 2/11/16.
 */
public class AudioRecordingThread extends Thread
{
    private AudioRecorder audioRecorder;
    private String writePath;
    private AudioRecord audioRecord;
    private byte[] audioData;
    private int bufferSize;

    public AudioRecordingThread(AudioRecorder audioRecorder, String writePath, byte[] audioData,
                                AudioRecord audioRecord, int bufferSize)
    {
        this.audioRecorder = audioRecorder;
        this.writePath = writePath;
        this.audioData = audioData;
        this.audioRecord = audioRecord;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run()
    {
        FileOutputStream outputStream = null;
        ByteArrayOutputStream recordingData = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(recordingData);

        try
        {
            outputStream = new FileOutputStream(writePath);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        while (audioRecorder.isRecording())
        {
            audioRecord.read(audioData, 0, audioData.length);

            try
            {
                outputStream.write(audioData, 0, bufferSize);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            dataStream.flush();
            dataStream.close();
            if (outputStream != null)
                outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
