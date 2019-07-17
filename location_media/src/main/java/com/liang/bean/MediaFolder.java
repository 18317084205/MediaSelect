package com.liang.bean;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.liang.loader.FolderCursorLoader;

import java.util.ArrayList;
import java.util.List;

public class MediaFolder implements Parcelable {
    public final long id;
    public final String path;
    public final String name;
    public long count;


    public MediaFolder(Cursor cursor) {
        this(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                cursor.getLong(cursor.getColumnIndex(FolderCursorLoader.COLUMN_COUNT)));
    }

    public MediaFolder(long id, String path, String name, long count) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.count = count;
    }

    protected MediaFolder(Parcel in) {
        id = in.readLong();
        path = in.readString();
        name = in.readString();
        count = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(path);
        dest.writeString(name);
        dest.writeLong(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaFolder> CREATOR = new Creator<MediaFolder>() {
        @Override
        public MediaFolder createFromParcel(Parcel in) {
            return new MediaFolder(in);
        }

        @Override
        public MediaFolder[] newArray(int size) {
            return new MediaFolder[size];
        }
    };

    public static List<MediaFolder> ofList(Cursor cursor) {
        List<MediaFolder> mediaFolders = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                mediaFolders.add(new MediaFolder(cursor));
            } while (cursor.moveToNext());
        }
        return mediaFolders;
    }
}
