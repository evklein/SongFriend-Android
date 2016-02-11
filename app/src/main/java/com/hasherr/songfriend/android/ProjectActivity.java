package com.hasherr.songfriend.android;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.hasherr.songfriend.android.adapter.CustomPagerAdapter;
import com.hasherr.songfriend.android.custom.activity.CustomMenuActivity;
import com.hasherr.songfriend.android.util.FileUtilities;

public class ProjectActivity extends CustomMenuActivity
{
    private String projectName;
    private String draftName;
    private String lyricPath;
    private String recordingPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        createPath();
        initToolbar(R.id.projectActivityToolbar, projectName + " - " + draftName);
        createDirectories();
        initTabs();
    }

    private void initTabs()
    {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.projectActivityTabLayout);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.logoGreen));
        tabLayout.addTab(tabLayout.newTab().setText("Write"));
        tabLayout.addTab(tabLayout.newTab().setText("Record"));
        tabLayout.addTab(tabLayout.newTab().setText("Perform"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        initViewPager(tabLayout);
    }

    private void initViewPager(TabLayout tabLayout)
    {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.projectActivityViewPager);
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), lyricPath, recordingPath);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
    }

    @Override
    protected void initValues()
    {
        projectName = FileUtilities.getDirectoryAtLevel(getIntent().getExtras().getString(FileUtilities.DIRECTORY_TAG), FileUtilities.PROJECT_LEVEL);
        draftName = FileUtilities.getDirectoryAtLevel(getIntent().getExtras().getString(FileUtilities.DIRECTORY_TAG), FileUtilities.DRAFT_LEVEL);
    }

    @Override
    protected void createPath()
    {
        path = getIntent().getExtras().getString(FileUtilities.DIRECTORY_TAG);
    }

    private void createDirectories()
    {
        lyricPath = path + "/lyrics";
        recordingPath = path + "/recording";
        FileUtilities.createDirectory(lyricPath);
        FileUtilities.createDirectory(recordingPath);
    }
}
