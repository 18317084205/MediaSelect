package com.liang.midia;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;

import com.liang.loader.MediaCursorLoader;

import java.util.Set;

public class MediaCollection extends LoaderCollection {
    private static final String TAG = MediaCollection.class.getSimpleName();
    private static final int LOADER_ID = 2;
    public static final String ALL_FOLDER_ID = String.valueOf(-1);
    private static final String FOLDER_ID = "folder_id";

    public MediaCollection(FragmentActivity activity, Set<MediaType> mimeType, LoaderCallbacks callbacks) {
        super(activity, mimeType, callbacks);
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
        if (context == null || cursor == null || mCallbacks == null) {
            return;
        }
        mCallbacks.onLoadFinished(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    @Override
    protected int setLoaderId() {
        return LOADER_ID;
    }
}
