package com.liang.media.listener;

import com.liang.bean.MediaInfo;

import java.util.List;

public interface OnMediaLoaderCallback {
    void onLoadMedia(List<MediaInfo> medias);
}
