package com.hasherr.songfriend.android.fragments.record;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasherr.songfriend.android.R;
import com.hasherr.songfriend.android.audio.AudioRecorder;
import com.hasherr.songfriend.android.audio.AudioTimerRunnable;
import com.hasherr.songfriend.android.project.Killable;
import com.hasherr.songfriend.android.project.PathListener;
import com.hasherr.songfriend.android.ui.listener.CustomOrientationListener;
import com.hasherr.songfriend.android.ui.listener.ErrorListener;
import com.hasherr.songfriend.android.ui.runnable.RecordDialogFragmentUIRunnable;
import com.hasherr.songfriend.android.utility.AudioUtilities;
import com.hasherr.songfriend.android.utility.FileUtilities;

import java.util.ArrayList;

/**
 * Created by evan on 1/19/16.
 */
public class RecordDialogFragment extends DialogFragment implements Killable, CustomOrientationListener, ErrorListener, PathListener
{
    private RecordDialogFragmentUIRunnable recordDialogFragmentUIRunnable;
    private View view;
    private AudioRecorder audioRecorder;
    private String recordPath;
    private boolean isRecording;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FragmentDialogFragmentStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_dialog_record, container, false);
        setRetainInstance(true);

        audioRecorder = new AudioRecorder();
        isRecording = false;
        recordDialogFragmentUIRunnable = new RecordDialogFragmentUIRunnable((ImageView) view.findViewById(R.id.blinkerView),
                (TextView) view.findViewById(R.id.recordDialogLengthTextView), new AudioTimerRunnable());

        initPath();
        setSpecifiedOrientation();
        initRecordButton();
        initSaveRecordingButton();
        initCancelButton();
        return view;
    }

    @Override
    public void initPath()
    {
        recordPath = getArguments().getString(FileUtilities.RECORDING_TAG);
    }

    @Override
    public void setSpecifiedOrientation()
    {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), getTheme())
        {
            @Override
            public void onBackPressed()
            {
                RecordDialogFragment.this.dismiss();
            }
        };

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    @Override
    public void dismiss()
    {
        kill();
        super.dismiss();
    }

    @Override
    public void kill()
    {
        ((EditText) getView().findViewById(R.id.recordingTitleEditText)).setText("");
        audioRecorder.kill();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

    private void initRecordButton()
    {
        final Button recordButton = (Button) view.findViewById(R.id.recordButton);
        final ImageView smallRecordButtonImageView = (ImageView) view.findViewById(R.id.blinkerView);
        recordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    if (!isRecording)
                    {
                        recordDialogFragmentUIRunnable.restart();
                        recordDialogFragmentUIRunnable.run();
                        recordButton.setBackgroundResource(R.drawable.record_button_on);
                        audioRecorder.startRecording();
                        isRecording = true;
                    } else
                    {
                        recordDialogFragmentUIRunnable.stop();
                        recordButton.setBackgroundResource(R.drawable.record_button_off);
                        smallRecordButtonImageView.setBackgroundResource(R.drawable.blinker_off);
                        audioRecorder.stopRecording();
                        isRecording = false;
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initCancelButton()
    {
        Button cancel = (Button) view.findViewById(R.id.cancelRecordingButton);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    private void initSaveRecordingButton()
    {
        Button save = (Button) view.findViewById(R.id.saveRecordButton);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!hasErrors())
                {
                    String path = recordPath + "/" + ((EditText) view.findViewById(R.id.recordingTitleEditText)).
                            getText().toString().trim() + ".wav";
                    audioRecorder.stopRecording();
                    audioRecorder.saveRecording(path);
                    getTargetFragment().onActivityResult(FileUtilities.MODIFY_REQUEST_CODE, Activity.RESULT_OK,
                            packageIntentRefreshData(path));
                    dismiss();
                }
            }
        });
    }

    @Override
    public boolean hasErrors()
    {
        EditText recordingTitleEditText = (EditText) view.findViewById(R.id.recordingTitleEditText);
        TextView errorView = (TextView) view.findViewById(R.id.recordDialogErrorTextView);
        ArrayList<String> previousRecordings = FileUtilities.getFileList(recordPath);
        String titleContents = recordingTitleEditText.getText().toString().trim();

        if (AudioUtilities.isEmpty(audioRecorder.getAudioData()))
        {
            errorView.setText("Recording has not been started.");
            return true;
        }
        else if (titleContents.equals(""))
        {
            errorView.setText("Please enter a title.");
            return true;
        }
        else if (titleContents.equals("/"))
        {
            errorView.setText("Invalid name.");
            return true;
        }

        for (String s : previousRecordings)
        {
            if (FileUtilities.getFileNameNoExtension(s, "wav").toLowerCase().equals(titleContents.toLowerCase()))
            {
                errorView.setText("That recording already exists.");
                return true;
            }
        }

        return false;
    }

    private Intent packageIntentRefreshData(String pathToPass)
    {
        Intent intent = new Intent();
        intent.putExtra(FileUtilities.MODIFY_RECORDING_TAG, pathToPass);
        return intent;
    }
}