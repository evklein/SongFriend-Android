package com.hasherr.songfriend.android.ui.visual;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by evan on 2/9/16.
 */
public class WindowSlideInAnimator
{
    public static final int SLIDE_RIGHT = 0;
    public static final int SLIDE_LEFT = 1;
    public static final int SLIDE_BOTTOM = 2;
    public static final int SLIDE_TOP = 3;

    public void animate(ViewGroup windowRoot, boolean hasFocus, int animation)
    {
        if (hasFocus)
        {
            startSlideInAnimation(windowRoot, animation);
        }
    }

    private void startSlideInAnimation(ViewGroup windowRoot, int animation)
    {
        ViewGroup contentRoot = (ViewGroup) windowRoot.getChildAt(0);

        for (int i = 0; i < contentRoot.getChildCount(); i++)
        {
            View view = contentRoot.getChildAt(i);

            switch (animation)
            {
                case SLIDE_RIGHT:
                    slideInFromRight(windowRoot, i, view);
                    break;
                case SLIDE_LEFT:
                    slideInFromLeft(windowRoot, i, view);
                    break;
                case SLIDE_BOTTOM:
                    slideInFromBottom(windowRoot, i, view);
                    break;
                case SLIDE_TOP:
                    slideInFromTop(windowRoot, i, view);
                    break;
                default:
                    Log.wtf("Animation Error in switch(animation) [WindowAnimator.java]", "Switch hit default.");
                    break;
            }
        }
    }

    private void slideInFromRight(ViewGroup windowRoot, int viewPosition, View view)
    {
        view.setTranslationX(-windowRoot.getWidth());
        view.setAlpha(0);
        view.animate().translationX(0).alpha(1).setStartDelay(300).setDuration(400 + 30 * viewPosition)
                .setInterpolator(new DecelerateInterpolator(2f)).start();
    }

    private void slideInFromLeft(ViewGroup windowRoot, int viewPosition, View view)
    {
        view.setTranslationX(windowRoot.getWidth());
        view.setAlpha(0);
        view.animate().translationX(0).alpha(1).setStartDelay(300).setDuration(400 + 30 * viewPosition)
                .setInterpolator(new DecelerateInterpolator(2f)).start();
    }

    private void slideInFromBottom(ViewGroup windowRoot, int viewPosition, View view)
    {
        view.setTranslationY(windowRoot.getHeight());
        view.setAlpha(0);
        view.animate().translationY(0).alpha(1).setStartDelay(300).setDuration(400 + 30 * viewPosition)
                .setInterpolator(new DecelerateInterpolator(2f)).start();
    }

    private void slideInFromTop(ViewGroup windowRoot, int viewPosition, View view)
    {
        view.setTranslationY(-windowRoot.getWidth());
        view.setAlpha(0);
        view.animate().translationY(0).alpha(1).setStartDelay(300).setDuration(400 + 30 * viewPosition)
                .setInterpolator(new DecelerateInterpolator(2f)).start();
    }

}
