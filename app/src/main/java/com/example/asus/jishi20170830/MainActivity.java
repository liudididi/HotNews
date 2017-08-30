package com.example.asus.jishi20170830;

import android.os.Handler;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import Adapter.Myxlv;
import Bean.News;
import Fragment.Fragment_left;
import Fragment.Fragment_right;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity implements XListView.IXListViewListener {
    @ViewInject(R.id.xlv)
    XListView xlv;
    private List<News> list=new ArrayList<>();
    private Handler h=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        x.view().inject(this);
        // 初始化控件
        initview();
        RequestParams params=new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("type","yule");
        params.addBodyParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        //post 请求
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                jiexi(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void initview() {
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        initmenu();
    }

    private void initmenu() {
        SlidingMenu menu=getSlidingMenu();
        //左fragment布局
        setBehindContentView(R.layout.left_count);
        getSupportFragmentManager().beginTransaction().add(R.id.left_count,new Fragment_left()).commit();
        //右fragment布局
        menu.setSecondaryMenu(R.layout.right_count);
        getSupportFragmentManager().beginTransaction().add(R.id.right_count,new Fragment_right()).commit();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置展示距屏幕多少距离
        menu.setBehindOffset(150);
        //设置渐变效果
        menu.setFadeDegree(0.35f);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
/*        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setFadeEnabled(true);
         menu.setBehindOffset(100);
        menu.attachToActivity(MainActivity.this,SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(1f);
        menu.setFadeEnabled(true);
        menu.setMenu(R.layout.left_count);*/
    }

    /**
     * 解析字符串
     * @param result json结果字符串
     */
    private void jiexi(String result) {
        try {
            JSONObject json=new JSONObject(result);
            JSONObject result1 = json.getJSONObject("result");
            JSONArray data = result1.getJSONArray("data");
            for (int i = 0; i <data.length(); i++) {
                JSONObject d= (JSONObject) data.get(i);
                News news=new News();
                news.title=d.optString("title");
                news.date=d.optString("date");
                news.thumbnail_pic_s=d.optString("thumbnail_pic_s");
                list.add(news);
            }
            if(list!=null){
                //添加适配器
                Myxlv ad=new Myxlv(MainActivity.this,list);
                xlv.setAdapter(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);

    }

    private void onLoad() {
        xlv.stopLoadMore();
        xlv.stopRefresh();
        xlv.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);
    }
}
