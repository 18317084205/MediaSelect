package com.liang.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.liang.media.MediaType;

import java.util.Set;


public class MediaCursorLoader extends CursorLoader {

    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String BUCKET_ORDER_BY = MediaStore.MediaColumns.DATE_ADDED + " DESC";

    private static final String[] PROJECTION = {
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.Audio.Media.DURATION
    };

    private static final String SELECTION_ALL =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String SELECTION =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String SELECTION_ALL_FOLDER =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND "
                    + " bucket_id=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String SELECTION_FOLDER =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND "
                    + " bucket_id=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String[] SELECTION_ALL_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO),
    };

    private static final String SELECTION_ALL_FOR_MEDIA_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String SELECTION_FOLDER_FOR_MEDIA_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND "
                    + " bucket_id=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    public MediaCursorLoader(@NonNull Context context, String selection, String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, BUCKET_ORDER_BY);
    }

    private static String getSelectionMediaType(String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID)
                ? SELECTION_ALL_FOR_MEDIA_TYPE : SELECTION_FOLDER_FOR_MEDIA_TYPE;
    }

    private static String getAllSelectionMediaType(String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID)
                ? SELECTION_ALL : SELECTION_ALL_FOLDER;
    }

    private static String get2SelectionMediaType(String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID)
                ? SELECTION : SELECTION_FOLDER;
    }

    private static String[] getSelectionArgsMediaType(int mimeType, String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID)
                ? new String[]{String.valueOf(mimeType)} : new String[]{String.valueOf(mimeType), folderId};
    }

    private static String[] get2SelectionArgsMediaType(String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID) ?
                new String[]{SELECTION_ALL_ARGS[0], SELECTION_ALL_ARGS[1]} :
                new String[]{SELECTION_ALL_ARGS[0], SELECTION_ALL_ARGS[1], folderId};
    }

    private static String[] getAllSelectionArgsMediaType(String folderId) {
        return folderId.equals(FolderCursorLoader.ALL_FOLDER_ID) ?
                SELECTION_ALL_ARGS : new String[]{SELECTION_ALL_ARGS[0],
                SELECTION_ALL_ARGS[1], SELECTION_ALL_ARGS[2], folderId};
    }

    public static CursorLoader newInstance(@NonNull Context context, String folderId, Set<MediaType> mimeType) {
        String selection;
        String[] selectionArgs;
        if (MediaType.onlyShowImages(mimeType)) {
            selection = getSelectionMediaType(folderId);
            selectionArgs = getSelectionArgsMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, folderId);
        } else if (MediaType.onlyShowVideos(mimeType)) {
            selection = getSelectionMediaType(folderId);
            selectionArgs = getSelectionArgsMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO, folderId);
        } else if (MediaType.onlyShowAudio(mimeType)) {
            selection = getSelectionMediaType(folderId);
            selectionArgs = getSelectionArgsMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO, folderId);
        } else if (MediaType.onlyShowImagesAndVideos(mimeType)) {
            selection = get2SelectionMediaType(folderId);
            selectionArgs = get2SelectionArgsMediaType(folderId);
        } else {
            selection = getAllSelectionMediaType(folderId);
            selectionArgs = getAllSelectionArgsMediaType(folderId);
        }

        return new MediaCursorLoader(context, selection, selectionArgs);
    }


}

