package com.hasherr.songfriend.android.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.List;

/**
 * Created by Evan on 2/16/2016.
 */
public class CustomDragArrayAdapter extends ArrayAdapter<String> implements Swappable
{
    private List<String> objects;

    public CustomDragArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects)
    {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public long getItemId(final int position)
    {
        return getItem(position).hashCode();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo)
    {
        String firstItem = objects.set(positionOne, getItem(positionTwo));
        notifyDataSetChanged();
        objects.set(positionTwo, firstItem);
    }
}
