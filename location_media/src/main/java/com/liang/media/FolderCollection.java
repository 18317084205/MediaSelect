package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;

import com.liang.bean.MediaFolder;
import com.liang.loader.FolderCursorLoader;
import com.liang.media.listener.OnFolderLoaderCallback;
import com.liang.media.listener.OnLoaderCallback;

import java.util.List;
import java.util.Set;


public class FolderCollection extends LoaderCollection {

    private OnFolderLoaderCallback folderLoaderCallback;

    public FolderCollection(FragmentActivity activity, Set<MediaType> mimeType, OnLoaderCallback callbacks, OnFolderLoaderCallback folderLoaderCallback) {
        super(activity, mimeType, callbacks);
        this.folderLoaderCallback = folderLoaderCallback;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        return FolderCursorLoader.newInstance(context, mimeType);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        if (mCallbacks != null) {
            mCallbacks.onLoadFinished(data);
        }

        if (folderLoaderCallback != null) {
            mExecutorService.execute(new Task(data));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public void load() {
        initLoader(null);
    }

    @Override
    protected int setLoaderId() {
        return 1;
    }

    private class Task implements Runnable {

        private Cursor cursor;

        public Task(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public void run() {
            final List<MediaFolder> mediaFolderList = MediaFolder.ofList(cursor);
            if (folderLoaderCallback != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        folderLoaderCallback.onLoadFolder(mediaFolderList);
                    }
                });
            }
        }
    }
}
