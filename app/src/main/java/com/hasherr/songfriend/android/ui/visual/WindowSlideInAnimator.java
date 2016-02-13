package com.hasherr.songfriend.android.ui.visual;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by evan on 2/9/16.
 */
public class WindowSlideInAnimator
{
    public void animate(ViewGroup windowRoot, boolean hasFocus)
    {
        if (hasFocus)
        {
            startSlideInAnimation(windowRoot);
        }
    }

    private void startSlideInAnimation(ViewGroup windowRoot)
    {
        ViewGroup contentRoot = (ViewGroup) windowRoot.getChildAt(0);

        for (int i = 0; i < contentRoot.getChildCount(); i++)
        {
            View view = contentRoot.getChildAt(i);
            animateSingleView(windowRoot, i, view);
        }
    }

    private void animateSingleView(ViewGroup windowRoot, int viewPosition, View view)
    {
        view.setTranslationY(windowRoot.getHeight());
        view.setAlpha(0);
        view.animate().translationY(0).alpha(1).setStartDelay(300).setDuration(400 + 30 * viewPosition)
                .setInterpolator(new DecelerateInterpolator(2f)).start();
    }
}
