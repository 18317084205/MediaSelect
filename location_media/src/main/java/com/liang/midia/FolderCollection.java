package com.liang.midia;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;

import com.liang.loader.FolderCursorLoader;

import java.util.Set;


public class FolderCollection extends LoaderCollection {

    public FolderCollection(FragmentActivity activity, Set<MediaType> mimeType, LoaderCallbacks callbacks) {
        super(activity, mimeType, callbacks);
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
        mCallbacks.onLoadFinished(data);
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

}
