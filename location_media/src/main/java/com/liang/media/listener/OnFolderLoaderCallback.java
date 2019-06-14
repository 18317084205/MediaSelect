package com.liang.media.listener;

import com.liang.bean.MediaFolder;

import java.util.List;

public interface OnFolderLoaderCallback {
    void onLoadFolder(List<MediaFolder> folders);
}
