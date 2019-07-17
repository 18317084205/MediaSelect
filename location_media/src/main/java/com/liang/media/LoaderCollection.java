package com.liang.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;

import com.liang.media.listener.OnLoaderCallback;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class LoaderCollection implements LoaderManager.LoaderCallbacks<Cursor> {

    protected final ExecutorService mExecutorService;
    protected final WeakReference<Context> mContext;
    protected final LoaderManager mLoaderManager;
    protected final Handler mHandler;
    protected OnLoaderCallback mCallbacks;
    protected final Set<MediaType> mimeType;
    private final int LOADER_ID;

    public LoaderCollection(FragmentActivity activity, Set<MediaType> mimeType, OnLoaderCallback callbacks) {
        this.mExecutorService = Executors.newSingleThreadExecutor();
        this.mHandler = new Handler(Looper.getMainLooper());
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
