package com.hasherr.songfriend.android.ui.handler;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Evan on 2/5/2016.
 */
public class ListHandler
{
    private ArrayAdapter<String> listAdapter;
    private ListView listView;
    private String contentPath;

    public ListHandler(ListView listView, ArrayAdapter<String> listAdapter, String contentPath)
    {
        this.listAdapter = listAdapter;
        this.listView = listView;
        listView.setAdapter(listAdapter);

        this.contentPath = contentPath;
    }

    public void initListViewItemClick(AdapterView.OnItemClickListener onItemClickListener)
    {
        listView.setOnItemClickListener(onItemClickListener);
    }

    public void initListViewLongItemClick(AdapterView.OnItemLongClickListener onItemLongClickListener)
    {
        listView.setOnItemLongClickListener(onItemLongClickListener);
    }


    public void refresh(ArrayList<String> list)
    {
        listAdapter.clear();
        listAdapter.addAll(list);
        listAdapter.notifyDataSetChanged();
    }
}
