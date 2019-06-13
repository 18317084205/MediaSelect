package com.liang.midia;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import com.liang.bean.MediaFolder;
import com.liang.bean.MediaInfo;
import com.liang.midia.listener.OnFolderLoaderCallback;
import com.liang.midia.listener.OnMediaLoaderCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MediaManager {

    public static void getMedia(FragmentActivity activity, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(activity, MediaType.ofAll(), mediaLoaderCallback);
    }

    public static void getMedia(FragmentActivity activity, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(activity, -1, mimeTypes, mediaLoaderCallback);
    }

    public static void getMedia(FragmentActivity activity, long folderId, Set<MediaType> mimeTypes, final OnMediaLoaderCallback mediaLoaderCallback) {
        MediaCollection mediaCollection = new MediaCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                if (mediaLoaderCallback != null) {
                    List<MediaInfo> medias = new ArrayList<>();
                    if (isDataValid(cursor)) {
                        if (cursor.moveToFirst()) {
                            do {
                                medias.add(new MediaInfo(cursor));
                            } while (cursor.moveToNext());
                        }
                    }
                    mediaLoaderCallback.onLoadMedia(medias);
                }
            }
        });
        MFragment.injectIfNeededIn(activity, mediaCollection);
        mediaCollection.load(folderId + "");
    }


    public static void getFolder(FragmentActivity activity, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(activity, MediaType.ofAll(), folderLoaderCallback);
    }

    public static void getFolder(FragmentActivity activity, Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback) {
        FolderCollection folderCollection = new FolderCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                if (folderLoaderCallback != null) {
                    List<MediaFolder> medias = new ArrayList<>();
                    if (isDataValid(cursor)) {
                        if (cursor.moveToFirst()) {
                            do {
                                medias.add(new MediaFolder(cursor));
                            } while (cursor.moveToNext());
                        }
                    }
                    folderLoaderCallback.onLoadFolder(medias);
                }
            }
        });
        MFragment.injectIfNeededIn(activity, folderCollection);
        folderCollection.load();
    }

    private static boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
