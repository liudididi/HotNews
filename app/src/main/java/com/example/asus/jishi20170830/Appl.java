package com.example.asus.jishi20170830;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by asus on 2017/8/30.
 */

public class Appl extends Application{
    public static  Context context;
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(false);
        //初始化
        initimageloader();
        MobSDK.init(this, "20a175d67fa67", "8958fcf2e554a4f6c54e2ae205d36cc3");
        UMShareAPI.get(this);
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
