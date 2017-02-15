package com.example.administrator.myeasydemo.main;

import android.app.Application;

import com.example.administrator.myeasydemo.model.CachePreferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CachePreferences.init(getApplicationContext());

        //初始化ImageLoader
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)/*开启硬盘缓存*/
                .cacheInMemory(true)/*开启内存缓存*/
                .resetViewBeforeLoading(true)/*加载前重置ImageView*/
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(4 * 1024 * 1024)//设置内存缓存的大小（4M）
                .defaultDisplayImageOptions(displayImageOptions)//设置默认的加载选项
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
