package com.liang.midia;

import android.database.Cursor;
import android.support.v4.app.Fragment;
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
        MediaCollection mediaCollection = getMediaCollection(mimeTypes, mediaLoaderCallback, activity);
        MFragment.injectIfNeededIn(activity, mediaCollection);
        mediaCollection.load(folderId + "");
    }

    public static void getMedia(Fragment fragment, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(fragment, MediaType.ofAll(), mediaLoaderCallback);
    }

    public static void getMedia(Fragment fragment, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(fragment, -1, mimeTypes, mediaLoaderCallback);
    }

    public static void getMedia(Fragment fragment, long folderId, Set<MediaType> mimeTypes, final OnMediaLoaderCallback mediaLoaderCallback) {
        MediaCollection mediaCollection = getMediaCollection(mimeTypes, mediaLoaderCallback, fragment.getActivity());
        MFragment.injectIfNeededIn(fragment, mediaCollection);
        mediaCollection.load(folderId + "");
    }

    private static MediaCollection getMediaCollection(Set<MediaType> mimeTypes, final OnMediaLoaderCallback mediaLoaderCallback, FragmentActivity activity) {
        return new MediaCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
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
    }


    public static void getFolder(Fragment fragment, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(fragment, MediaType.ofAll(), folderLoaderCallback);
    }

    public static void getFolder(Fragment fragment, Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback) {
        FolderCollection folderCollection = getFolderCollection(mimeTypes, folderLoaderCallback, fragment.getActivity());
        MFragment.injectIfNeededIn(fragment, folderCollection);
        folderCollection.load();
    }

    private static FolderCollection getFolderCollection(Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback, FragmentActivity activity) {
        return new FolderCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
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
    }


    public static void getFolder(FragmentActivity activity, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(activity, MediaType.ofAll(), folderLoaderCallback);
    }

    public static void getFolder(FragmentActivity activity, Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback) {
        FolderCollection folderCollection = getFolderCollection(mimeTypes, folderLoaderCallback, activity);
        MFragment.injectIfNeededIn(activity, folderCollection);
        folderCollection.load();
    }

    private static boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
