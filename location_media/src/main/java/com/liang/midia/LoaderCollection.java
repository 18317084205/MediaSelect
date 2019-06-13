package com.liang.midia;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import java.lang.ref.WeakReference;
import java.util.Set;

public abstract class LoaderCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    protected final WeakReference<Context> mContext;
    protected final LoaderManager mLoaderManager;
    protected LoaderCallbacks mCallbacks;
    protected final Set<MediaType> mimeType;
    private final int LOADER_ID;

    public LoaderCollection(FragmentActivity activity, Set<MediaType> mimeType, LoaderCallbacks callbacks) {
        this.mContext = new WeakReference<Context>(activity);
        this.mimeType = mimeType;
        this.mLoaderManager = activity.getSupportLoaderManager();
        this.mCallbacks = callbacks;
        this.LOADER_ID = setLoaderId();
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
    }

    protected abstract int setLoaderId();

    public interface LoaderCallbacks {
        void onLoadFinished(Cursor cursor);
    }

    protected void initLoader(Bundle bundle) {
        if (mLoaderManager != null) {
            mLoaderManager.initLoader(LOADER_ID, bundle, this);
        }
    }

    public void onDestroy() {
        if (mLoaderManager != null) {
            mLoaderManager.destroyLoader(LOADER_ID);
        }
        mCallbacks = null;
    }
}
