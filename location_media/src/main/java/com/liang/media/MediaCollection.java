package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;

import com.liang.bean.MediaInfo;
import com.liang.loader.MediaCursorLoader;
import com.liang.media.listener.OnLoaderCallback;
import com.liang.media.listener.OnMediaLoaderCallback;

import java.util.List;
import java.util.Set;

public class MediaCollection extends LoaderCollection {
    private static final String TAG = MediaCollection.class.getSimpleName();
    private static final int LOADER_ID = 2;
    public static final String ALL_FOLDER_ID = String.valueOf(-1);
    private static final String FOLDER_ID = "folder_id";
    private OnMediaLoaderCallback mediaLoaderCallback;

    public MediaCollection(FragmentActivity activity, Set<MediaType> mimeType, OnLoaderCallback callbacks, OnMediaLoaderCallback mediaLoaderCallback) {
        super(activity, mimeType, callbacks);
        this.mediaLoaderCallback = mediaLoaderCallback;
    }

    public void load(String folderId) {
        Bundle bundle = new Bundle();
        bundle.putString(FOLDER_ID, folderId);
        initLoader(bundle);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        String id = bundle.getString(FOLDER_ID, ALL_FOLDER_ID);
        return MediaCursorLoader.newInstance(context, id, mimeType);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Context context = mContext.get();
        if (context == null || cursor == null) {
            return;
        }
        if (mCallbacks != null) {
            mCallbacks.onLoadFinished(cursor);
        }

        if (mediaLoaderCallback != null) {
            mExecutorService.execute(new Task(cursor));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    @Override
    protected int setLoaderId() {
        return LOADER_ID;
    }

    private class Task implements Runnable {

        private Cursor cursor;

        public Task(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public void run() {
            final List<MediaInfo> mediaInfoList = MediaInfo.ofList(cursor);
            if (mediaLoaderCallback != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaLoaderCallback.onLoadMedia(mediaInfoList);
                    }
                });
            }
        }
    }
}
