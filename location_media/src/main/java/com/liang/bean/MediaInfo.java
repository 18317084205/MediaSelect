package com.liang.bean;import android.content.ContentUris;import android.database.Cursor;import android.net.Uri;import android.os.Parcel;import android.os.Parcelable;import android.provider.MediaStore;import com.liang.media.MediaType;import java.util.ArrayList;import java.util.List;public class MediaInfo implements Parcelable {    public final long id;    public final String name;    public final String mimeType;    public final Uri uri;    public final String path;    public final long size;    public final long duration;    public MediaInfo(Cursor cursor) {        this(cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)),                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)),                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)),                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),                cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)),                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));    }    public MediaInfo(long id, String name, String mimeType, String path, long size, long duration) {        this.id = id;        this.name = name;        this.mimeType = mimeType;        this.path = path;        this.size = size;        this.duration = duration;        Uri contentUri;        if (MediaType.isImage(mimeType)) {            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;        } else if (MediaType.isVideo(mimeType)) {            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;        } else if (MediaType.isAudio(mimeType)) {            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;        } else {            contentUri = MediaStore.Files.getContentUri("external");        }        this.uri = ContentUris.withAppendedId(contentUri, id);    }    public static List<MediaInfo> ofList(Cursor cursor) {        List<MediaInfo> mediaInfoList = new ArrayList<>();        if (cursor.moveToFirst()) {            do {                mediaInfoList.add(new MediaInfo(cursor));            } while (cursor.moveToNext());        }        return mediaInfoList;    }    protected MediaInfo(Parcel in) {        id = in.readLong();        name = in.readString();        mimeType = in.readString();        uri = in.readParcelable(Uri.class.getClassLoader());        path = in.readString();        size = in.readLong();        duration = in.readLong();    }    @Override    public void writeToParcel(Parcel dest, int flags) {        dest.writeLong(id);        dest.writeString(name);        dest.writeString(mimeType);        dest.writeParcelable(uri, flags);        dest.writeString(path);        dest.writeLong(size);        dest.writeLong(duration);    }    @Override    public int describeContents() {        return 0;    }    public static final Creator<MediaInfo> CREATOR = new Creator<MediaInfo>() {        @Override        public MediaInfo createFromParcel(Parcel in) {            return new MediaInfo(in);        }        @Override        public MediaInfo[] newArray(int size) {            return new MediaInfo[size];        }    };    @Override    public boolean equals(Object obj) {        if (!(obj instanceof MediaInfo)) {            return false;        }        MediaInfo info = (MediaInfo) obj;        return id == info.id                && (mimeType != null && mimeType.equals(info.mimeType)                || (mimeType == null && info.mimeType == null))                && (uri != null && uri.equals(info.uri)                || (uri == null && info.uri == null))                && (path != null && path.equals(info.path)                || (path == null && info.path == null))                && size == info.size                && duration == info.duration;    }    @Override    public int hashCode() {        int result = 1;        result = 31 * result + Long.valueOf(id).hashCode();        if (mimeType != null) {            result = 31 * result + mimeType.hashCode();        }        result = 31 * result + uri.hashCode();        result = 31 * result + Long.valueOf(size).hashCode();        result = 31 * result + Long.valueOf(duration).hashCode();        return result;    }}