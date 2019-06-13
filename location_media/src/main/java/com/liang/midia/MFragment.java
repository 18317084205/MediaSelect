package com.liang.midia;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class MFragment extends Fragment {
    private static final String FRAGMENT_TAG = "Media_MFragment";
    private static LoaderCollection sLoaderCollection;

    public static void injectIfNeededIn(FragmentActivity activity, LoaderCollection loaderCollection) {
        if (sLoaderCollection != null) {
            sLoaderCollection.onDestroy();
            sLoaderCollection = null;
        }
        sLoaderCollection = loaderCollection;
        FragmentManager manager = activity.getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new MFragment();
            manager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
            manager.executePendingTransactions();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sLoaderCollection != null) {
            Log.e(FRAGMENT_TAG, "onDestroy: " + sLoaderCollection.setLoaderId());
            sLoaderCollection.onDestroy();
        }
        sLoaderCollection = null;
    }
}
