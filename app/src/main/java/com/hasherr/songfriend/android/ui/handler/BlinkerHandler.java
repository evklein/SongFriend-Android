package com.hasherr.songfriend.android.ui.handler;

import android.widget.ImageView;
import com.hasherr.songfriend.android.R;

/**
 * Created by Evan on 2/8/2016.
 */
public class BlinkerHandler
{
    ImageView blinkerView;
    int blinkerCount;
    boolean isBlinkerOn;

    public BlinkerHandler(ImageView blinkerView)
    {
        this.blinkerView = blinkerView;
        blinkerCount = 1;
        isBlinkerOn = true;
    }

    public void manageBlinker()
    {
        blinkerCount++;
        if (blinkerCount >= 10)
        {
            if (isBlinkerOn)
            {
                isBlinkerOn = false;
                blinkerView.setBackgroundResource(R.drawable.blinker_off);
            }
            else
            {
                isBlinkerOn = true;
                blinkerView.setBackgroundResource(R.drawable.blinker_on);
            }
            blinkerCount = 1;
        }
    }
}
