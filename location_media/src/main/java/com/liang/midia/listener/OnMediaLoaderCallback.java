package com.liang.midia.listener;

import com.liang.bean.MediaInfo;

import java.util.List;

public interface OnMediaLoaderCallback<Media extends MediaInfo> {
    void onLoadMedia(List<Media> medias);
}
