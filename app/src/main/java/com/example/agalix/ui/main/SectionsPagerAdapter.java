package com.example.agalix.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.agalix.DownloadActivity;
import com.example.agalix.R;
import com.example.agalix.StreamActivity;
import com.example.agalix.VideoActivity;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DownloadActivity download = new DownloadActivity();
                return download;
            case 1:
                VideoActivity video = new VideoActivity();
                return video;
//            case 2:
//                RestoActivity resto = new RestoActivity();
//                return resto;
//            case 3:
//                EventActivity event = new EventActivity();
//                return event;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Download";
            case 1:
                return "Stream";
//            case 2:
//                return "Resto";
//            case 3:
//                return "Event";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}