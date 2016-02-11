package com.hasherr.songfriend.android.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import com.hasherr.songfriend.android.project.Killable;
import com.hasherr.songfriend.android.util.FileUtilities;

import java.io.*;

/**
 * Created by evan on 1/20/16.
 */
public class AudioRecorder implements Killable
{
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final String TEMP_DIRECTORY = FileUtilities.PROJECT_DIRECTORY + "/Temps";

    private AudioRecord audioRecord;
    private ByteArrayOutputStream recordingData;
    private Thread recordingThread;
    private boolean isRecording;
    private byte[] audioData;
    private int recordingCounter;
    private int bufferSize;

    public AudioRecorder()
    {
        FileUtilities.createDirectory(TEMP_DIRECTORY);
        recordingCounter = 0;
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL, ENCODING);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL, ENCODING, bufferSize);
        audioData = new byte[bufferSize];
        isRecording = false;
    }

    private void createRecordingRunnable()
    {
        recordingThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                FileOutputStream outputStream = null;
                recordingData = new ByteArrayOutputStream();
                DataOutputStream dataStream = new DataOutputStream(recordingData);

                try
                {
                    outputStream = new FileOutputStream(TEMP_DIRECTORY + "/" + recordingCounter + ".pcm");
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

                while (isRecording)
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

                recordingCounter++;
            }
        });
    }

    public void startRecording()
    {
        isRecording = true;
        createRecordingRunnable();
        audioRecord.startRecording();
        recordingThread.start();
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
                File individualPCM = new File(TEMP_DIRECTORY + "/" + i + ".pcm");
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
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(outputPath);
            dataOutputStream = new DataOutputStream(fileOutputStream);

            /* Header */
            dataOutputStream.writeBytes("RIFF");
            dataOutputStream.write(intToByteArray(36 + (length * 1 * 16 / 8)), 0, 4);
            dataOutputStream.writeBytes("WAVE");
            dataOutputStream.writeBytes("fmt ");
            dataOutputStream.write(intToByteArray(16), 0, 4);
            dataOutputStream.write(shortToByteArray((short) 1), 0, 2);
            dataOutputStream.write(shortToByteArray((short) 1), 0, 2);
            dataOutputStream.write(intToByteArray(SAMPLE_RATE), 0, 4);
            dataOutputStream.write(intToByteArray(SAMPLE_RATE * 1 * 16 / 8), 0, 4);
            dataOutputStream.write(shortToByteArray((short) (1 * 16 / 8)), 0, 2);
            dataOutputStream.write(shortToByteArray((short) 16), 0, 2);
            dataOutputStream.writeBytes("data");
            dataOutputStream.write(intToByteArray(length), 0, 4);

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

    @Override
    public void kill()
    {
        stopRecording();
        FileUtilities.delete(new File(TEMP_DIRECTORY));
    }

    private byte[] intToByteArray(int val)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (val & 0x00ff);
        bytes[1] = (byte) ((val >> 8) & 0x000000ff);
        bytes[2] = (byte) ((val >> 16) & 0x000000ff);
        bytes[3] = (byte) ((val >> 24) & 0x000000ff);
        return bytes;
    }

    private byte[] shortToByteArray(short val)
    {
        byte[] bytes = new byte[]
        {
            (byte)(val & 0xff),
            (byte)((val >>> 8) & 0xff)
        };
        return bytes;
    }

    public boolean isEmpty()
    {
        boolean isEmpty = true;
        for (Byte b : audioData)
        {
            if (b != 0)
                isEmpty = false;
        }
        return isEmpty;
    }
}
