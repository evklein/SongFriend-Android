package com.hasherr.songfriend.android.audio;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.hasherr.songfriend.android.project.Killable;
import com.hasherr.songfriend.android.utility.AudioUtilities;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.io.*;

/**
 * Created by evan on 1/20/16.
 */
public class AudioRecorder implements Killable
{
    private Thread recordingThread;
    private AudioRecord audioRecord;
    private byte[] audioData;
    private boolean isRecording;
    private int recordingCounter;
    private int bufferSize;

    public AudioRecorder()
    {
        FileUtilities.createDirectory(AudioUtilities.TEMP_AUDIO_PATH);
        bufferSize = AudioRecord.getMinBufferSize(AudioUtilities.SAMPLE_RATE, AudioUtilities.CHANNEL,
                AudioUtilities.ENCODING);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, AudioUtilities.SAMPLE_RATE, AudioUtilities.CHANNEL,
                AudioUtilities.ENCODING, bufferSize);
        audioData = new byte[bufferSize];
        isRecording = false;
        recordingCounter = 0;
    }

    public void startRecording()
    {
        isRecording = true;
        recordingThread = new AudioRecordingThread(this, AudioUtilities.TEMP_AUDIO_PATH + "/" + recordingCounter + ".pcm",
                audioData, audioRecord, bufferSize);
        audioRecord.startRecording();
        recordingThread.start();
        recordingCounter++;
    }

    public void stopRecording()
    {
        isRecording = false;
        audioRecord.stop();
        recordingThread = null;
    }

    public void saveRecording(String filePath)
    {
        byte[] audioData = combinePCMData();
        writeWAVFile(filePath, audioData.length, audioData);
    }

    public boolean isRecording()
    {
        return isRecording;
    }

    public byte[] getAudioData()
    {
        return audioData;
    }

    @Override
    public void kill()
    {
        stopRecording();
        FileUtilities.delete(new File(AudioUtilities.TEMP_AUDIO_PATH));
    }

    private byte[] combinePCMData()
    {
        byte[] combinedData = null;
        int totalDataLength = 0;

        try
        {
            FileInputStream[] recordingInputStreams = new FileInputStream[recordingCounter];
            byte[][] individualPCMData = new byte[recordingCounter][];

            // Create individual PCM byte[] arrays
            for (int i = 0; i < recordingCounter; i++)
            {
                File individualPCM = new File(AudioUtilities.TEMP_AUDIO_PATH + "/" + i + ".pcm");
                recordingInputStreams[i] = new FileInputStream(individualPCM);
                int partialLength = (int) individualPCM.length();
                individualPCMData[i] = new byte[partialLength];
                recordingInputStreams[i].read(individualPCMData[i]);
                totalDataLength += partialLength;
            }

            // Combine all PCM byte data from individualPCMData[][]combinedData = new byte[totalDataLength];
            combinedData = new byte[totalDataLength];
            int byteCount = 0;
            for (int i = 0; i < individualPCMData.length; i++)
                for (int j = 0; j < individualPCMData[i].length; j++)
                    combinedData[byteCount++] = individualPCMData[i][j];
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return combinedData;
    }

    private void writeWAVFile(String outputPath, int length, byte[] data)
    {
        FileOutputStream fileOutputStream;
        DataOutputStream dataOutputStream;

        try
        {
            fileOutputStream = new FileOutputStream(outputPath);
            dataOutputStream = new DataOutputStream(fileOutputStream);

            /* Header */
            dataOutputStream.writeBytes("RIFF");
            dataOutputStream.write(AudioUtilities.intToByteArray(36 + (length * 1 * 16 / 8)), 0, 4);
            dataOutputStream.writeBytes("WAVE");
            dataOutputStream.writeBytes("fmt ");
            dataOutputStream.write(AudioUtilities.intToByteArray(16), 0, 4);
            dataOutputStream.write(AudioUtilities.shortToByteArray((short) 1), 0, 2);
            dataOutputStream.write(AudioUtilities.shortToByteArray((short) 1), 0, 2);
            dataOutputStream.write(AudioUtilities.intToByteArray(AudioUtilities.SAMPLE_RATE), 0, 4);
            dataOutputStream.write(AudioUtilities.intToByteArray(AudioUtilities.SAMPLE_RATE * 1 * 16 / 8), 0, 4);
            dataOutputStream.write(AudioUtilities.shortToByteArray((short) (1 * 16 / 8)), 0, 2);
            dataOutputStream.write(AudioUtilities.shortToByteArray((short) 16), 0, 2);
            dataOutputStream.writeBytes("data");
            dataOutputStream.write(AudioUtilities.intToByteArray(length), 0, 4);

            /* Write Data*/
            dataOutputStream.write(data);

            /* Kill outputStream */
            dataOutputStream.flush();
            dataOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
