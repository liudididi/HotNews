package com.example.asus.jishi20170830;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by asus on 2017/8/30.
 */

public class Appl extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(false);
        //初始化
        initimageloader();
    }

    private void initimageloader() {
        DisplayImageOptions option=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration con=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(option)
                .build();
        ImageLoader.getInstance().init(con);
    }
}
