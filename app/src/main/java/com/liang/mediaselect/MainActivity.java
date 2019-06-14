package com.liang.mediaselect;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.liang.bean.MediaFolder;
import com.liang.bean.MediaInfo;
import com.liang.midia.MediaManager;
import com.liang.midia.MediaType;
import com.liang.midia.listener.OnFolderLoaderCallback;
import com.liang.midia.listener.OnMediaLoaderCallback;

import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getAudios(View view) {
        MediaManager.getFolder(this, new OnFolderLoaderCallback<MediaFolder>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLoadFolder(List<MediaFolder> folders) {
                folders.forEach(new Consumer<MediaFolder>() {
                    @Override
                    public void accept(MediaFolder mediaFolder) {
                        Log.e("MainActivity", "mediaFolder: " + mediaFolder.name);
                    }
                });
            }
        });
    }

    public void getVideos(View view) {
        MediaManager.getMedia(this, MediaType.ofVideo(), new OnMediaLoaderCallback<MediaInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLoadMedia(List<MediaInfo> medias) {
                medias.forEach(new Consumer<MediaInfo>() {
                    @Override
                    public void accept(MediaInfo mediaInfo) {
                        Log.e("MainActivity", "mediaInfo: " + mediaInfo.name);
                    }
                });
            }
        });
    }

    public void getImages(View view) {
        MediaManager.getMedia(this, MediaType.ofImage(), new OnMediaLoaderCallback<MediaInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLoadMedia(List<MediaInfo> medias) {
                medias.forEach(new Consumer<MediaInfo>() {
                    @Override
                    public void accept(MediaInfo mediaInfo) {
                        Log.e("MainActivity", "mediaInfo: " + mediaInfo.name);
                    }
                });
            }
        });
    }
}
