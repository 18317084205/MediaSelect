package com.liang.media;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.liang.bean.MediaFolder;
import com.liang.bean.MediaInfo;
import com.liang.media.listener.OnFolderLoaderCallback;
import com.liang.media.listener.OnLoaderCallback;
import com.liang.media.listener.OnMediaLoaderCallback;

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

    public static void getMedia(FragmentActivity activity, long folderId, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(activity, folderId, mimeTypes, mediaLoaderCallback, null);
    }


    public static void getMedia(FragmentActivity activity, OnLoaderCallback loaderCallback) {
        getMedia(activity, MediaType.ofAll(), loaderCallback);
    }

    public static void getMedia(FragmentActivity activity, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getMedia(activity, -1, mimeTypes, loaderCallback);
    }

    public static void getMedia(FragmentActivity activity, long folderId, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getMedia(activity, folderId, mimeTypes, null, loaderCallback);
    }

    public static void getMedia(FragmentActivity activity, long folderId, Set<MediaType> mimeTypes, final OnMediaLoaderCallback mediaLoaderCallback, OnLoaderCallback loaderCallback) {
        MediaCollection mediaCollection = getMediaCollection(mimeTypes, mediaLoaderCallback, loaderCallback, activity);
        MFragment.injectIfNeededIn(activity, mediaCollection);
        mediaCollection.load(folderId + "");
    }


    public static void getMedia(Fragment fragment, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(fragment, MediaType.ofAll(), mediaLoaderCallback);
    }

    public static void getMedia(Fragment fragment, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(fragment, -1, mimeTypes, mediaLoaderCallback);
    }

    public static void getMedia(Fragment fragment, long folderId, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback) {
        getMedia(fragment, folderId, mimeTypes, mediaLoaderCallback, null);
    }

    public static void getMedia(Fragment fragment, OnLoaderCallback loaderCallback) {
        getMedia(fragment, MediaType.ofAll(), loaderCallback);
    }

    public static void getMedia(Fragment fragment, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getMedia(fragment, -1, mimeTypes, loaderCallback);
    }

    public static void getMedia(Fragment fragment, long folderId, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getMedia(fragment, folderId, mimeTypes, null, loaderCallback);
    }

    public static void getMedia(Fragment fragment, long folderId, Set<MediaType> mimeTypes, OnMediaLoaderCallback mediaLoaderCallback, OnLoaderCallback loaderCallback) {
        MediaCollection mediaCollection = getMediaCollection(mimeTypes, mediaLoaderCallback, loaderCallback, fragment.getActivity());
        MFragment.injectIfNeededIn(fragment, mediaCollection);
        mediaCollection.load(folderId + "");
    }

    private static MediaCollection getMediaCollection(Set<MediaType> mimeTypes, final OnMediaLoaderCallback mediaLoaderCallback, final OnLoaderCallback loaderCallback, FragmentActivity activity) {
        return new MediaCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
            @Override
            public void onLoadFinished(Cursor cursor) {

                if (loaderCallback != null && isDataValid(cursor)) {
                    loaderCallback.onLoadFinished(cursor);
                }

                if (mediaLoaderCallback != null && isDataValid(cursor)) {
                    List<MediaInfo> medias = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            medias.add(new MediaInfo(cursor));
                        } while (cursor.moveToNext());
                    }
                    mediaLoaderCallback.onLoadMedia(medias);
                }
            }
        });
    }


    public static void getFolder(FragmentActivity activity, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(activity, MediaType.ofAll(), folderLoaderCallback);
    }

    public static void getFolder(FragmentActivity activity, Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(activity, mimeTypes, folderLoaderCallback, null);
    }

    public static void getFolder(FragmentActivity activity, OnLoaderCallback loaderCallback) {
        getFolder(activity, MediaType.ofAll(), loaderCallback);
    }

    public static void getFolder(FragmentActivity activity, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getFolder(activity, mimeTypes, null, loaderCallback);
    }

    public static void getFolder(FragmentActivity activity, Set<MediaType> mimeTypes, OnFolderLoaderCallback folderLoaderCallback, OnLoaderCallback loaderCallback) {
        FolderCollection folderCollection = getFolderCollection(mimeTypes, folderLoaderCallback, loaderCallback, activity);
        MFragment.injectIfNeededIn(activity, folderCollection);
        folderCollection.load();
    }


    public static void getFolder(Fragment fragment, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(fragment, MediaType.ofAll(), folderLoaderCallback);
    }

    public static void getFolder(Fragment fragment, Set<MediaType> mimeTypes, OnFolderLoaderCallback folderLoaderCallback) {
        getFolder(fragment, mimeTypes, folderLoaderCallback, null);
    }

    public static void getFolder(Fragment fragment, OnLoaderCallback loaderCallback) {
        getFolder(fragment, MediaType.ofAll(), loaderCallback);
    }

    public static void getFolder(Fragment fragment, Set<MediaType> mimeTypes, OnLoaderCallback loaderCallback) {
        getFolder(fragment, mimeTypes, null, loaderCallback);
    }

    public static void getFolder(Fragment fragment, Set<MediaType> mimeTypes, OnFolderLoaderCallback folderLoaderCallback, OnLoaderCallback loaderCallback) {
        FolderCollection folderCollection = getFolderCollection(mimeTypes, folderLoaderCallback, loaderCallback, fragment.getActivity());
        MFragment.injectIfNeededIn(fragment, folderCollection);
        folderCollection.load();
    }

    private static FolderCollection getFolderCollection(Set<MediaType> mimeTypes, final OnFolderLoaderCallback folderLoaderCallback, final OnLoaderCallback loaderCallback, FragmentActivity activity) {
        return new FolderCollection(activity, mimeTypes, new LoaderCollection.LoaderCallbacks() {
            @Override
            public void onLoadFinished(Cursor cursor) {

                if (loaderCallback != null && isDataValid(cursor)) {
                    loaderCallback.onLoadFinished(cursor);
                }

                if (folderLoaderCallback != null && isDataValid(cursor)) {
                    List<MediaFolder> medias = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            medias.add(new MediaFolder(cursor));
                        } while (cursor.moveToNext());
                    }
                    folderLoaderCallback.onLoadFolder(medias);
                }
            }
        });
    }

    private static boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }
}
