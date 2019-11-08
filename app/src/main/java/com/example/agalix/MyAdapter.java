package com.example.agalix;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class MyAdapter extends ArrayAdapter {
    private Context mContext;
    String[] uploadPDFS;
    private String link;

    public MyAdapter(@NonNull Context mContext, int resource, String[] uploadPDFS, String link) {
        super(mContext, resource);
        this.mContext = mContext;
        this.uploadPDFS = uploadPDFS;
        this.link = link;
    }

    @Override
    public Object getItem(int position){
        String name = uploadPDFS[position];
        String mlink = link;
        return String.format("%s \n Serves great: %s", name, mlink);
    }

    @Override
    public int getCount(){
        return uploadPDFS.length;
    }
}
