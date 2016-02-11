package com.hasherr.songfriend.android.utility;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by Evan on 1/3/2016.
 */
public class FileUtilities
{
    public final static String INTERNAL_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String PROJECT_DIRECTORY = INTERNAL_DIRECTORY + "/SongFriend";
    public final static String DIRECTORY_TAG = "directory_tag";
    public final static String RECORDING_TAG = "recording_tag";
    public final static int MODIFY_REQUEST_CODE = 7743;
    public final static int PROJECT_LEVEL = 1;
    public final static int DRAFT_LEVEL = 2;

    public static ArrayList<String> getDirectoryList(String baseDirectory)
    {
        ArrayList<String> fileNames = new ArrayList<String>();
        File dir = new File(baseDirectory);
        File[] allDirs = dir.listFiles();
        for (File f : allDirs)
            if (f.isDirectory())
                fileNames.add(f.getName());
        return fileNames;
    }

    public static ArrayList<String> getFileList(String baseDirectory)
    {
        ArrayList<String> fileNames = new ArrayList<String>();
        File dir = new File(baseDirectory);
        File[] allFiles = dir.listFiles();
        for (File f : allFiles)
            fileNames.add(f.getName());
        return fileNames;
    }

    public static ArrayList<String> getPathList(String baseDirectory)
    {
        ArrayList<String> fileNames = getFileList(baseDirectory);
        ArrayList<String> paths = new ArrayList<>();
        for (int i = 0; i < fileNames.size(); i++)
            paths.add(baseDirectory + "/" + fileNames.get(i));
        return paths;
    }

    public static ArrayList<String> getFileList(File[] allFiles)
    {
        ArrayList<String> fileNames = new ArrayList<>();
        for (File f : allFiles)
            fileNames.add(f.getName());
        return fileNames;
    }

    public static void delete(File directoryToDelete)
    {
        if (directoryToDelete.isDirectory())
        {
            File[] allFiles = directoryToDelete.listFiles();
            for (File f : allFiles)
            {
                if (f.isDirectory())
                    delete(f);
                else
                    f.delete();
            }
        }
        directoryToDelete.delete();
    }

    // Splits a directory into pieces and then reads the particular directory at the specified level(s).
    public static String getDirectoryAtLevel(String directoryPath, int level)
    {
        File directoryToSearch = new File(directoryPath);
        String[] allDirs = directoryToSearch.getAbsolutePath().split("/");
        ArrayList<String> allDirectories = new ArrayList<>();
        for (String s : allDirs)
            allDirectories.add(s);
        int returnItemIndex = 0;

        if (level == FileUtilities.PROJECT_LEVEL)
            returnItemIndex = allDirectories.indexOf("SongFriend") + 1;
        else if (level == FileUtilities.DRAFT_LEVEL)
            returnItemIndex = allDirectories.indexOf("SongFriend") + 2;
        String directoryNameToReturn = allDirectories.get(returnItemIndex);
        return directoryNameToReturn;
    }

    public static void createDirectory(String path)
    {
        File directory = new File(path);
        if (!directory.exists())
            directory.mkdir();
    }

    public static void createDirectory(Hashtable<String, String> elements, String directoryToWriteTo) throws IOException
    {
        File directory = new File(directoryToWriteTo);
        directory.mkdir();
        writeInfoFile(elements, directory);
    }

    public static void writeInfoFile(Hashtable<String, String> elements, File directoryToWriteTo) throws IOException
    {
        File infoFile = new File(directoryToWriteTo.getAbsolutePath() + "/" + elements.get("title") + ".txt");
        FileOutputStream outputStream = new FileOutputStream(infoFile);
        byte[] indent = "\n".getBytes();

        Iterator<Map.Entry<String, String>> iterator = elements.entrySet().iterator();
        while (iterator.hasNext())
        {
            String element = iterator.next().getValue();
            outputStream.write(element.getBytes());
            outputStream.write(indent);
        }
        outputStream.close();
    }

    public static String getFileNameNoExtension(String fileName, String fileType)
    {
        return fileName.substring(0, fileName.indexOf("." + fileType));
    }

    public static ArrayList<String> getSortedFilePaths(String path)
    {
        File[] files = new File(path).listFiles();
        Arrays.sort(files, new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });

        ArrayList<String> orderedPaths = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
            orderedPaths.add(files[i].getAbsolutePath());
        return orderedPaths;
    }

    public static ArrayList<String> getSortedFilePathsReverse(String path)
    {
        File[] files = new File(path).listFiles();
        Arrays.sort(files, new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });

        ArrayList<String> orderedPaths = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
            orderedPaths.add(files[i].getAbsolutePath());
        Collections.reverse(orderedPaths);
        return orderedPaths;
    }

    public static ArrayList<String> reverseArray(ArrayList<String> array)
    {
        Collections.reverse(array);
        return array;
    }
}
