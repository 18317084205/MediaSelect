package com.liang.midia.listener;

import com.liang.bean.MediaFolder;

import java.util.List;

public interface OnFolderLoaderCallback<Folder extends MediaFolder> {
    void onLoadFolder(List<Folder> folders);
}
