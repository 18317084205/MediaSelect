package com.liang.media.listener;

import android.database.Cursor;


public interface OnLoaderCallback {
    void onLoadFinished(Cursor cursor);
}
