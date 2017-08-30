package com.example.asus.jishi20170830;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.xlv)
    XListView xlv;
    private List<News> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        x.view().inject(this);
        initview();
        RequestParams params=new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("key","22a108244dbb8d1f49967cd74a0c144d");
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
    }

    private void jiexi(String result) {
        try {
            JSONObject json=new JSONObject(result);
            JSONObject result1 = json.getJSONObject("result");
            JSONArray data = result1.getJSONArray("data");
            for (int i = 0; i <data.length(); i++) {
                JSONObject d= (JSONObject) data.get(i);
                News news=new News();
                news.title=d.optString("title");
                news.thumbnail_pic_s=d.optString("thumbnail_pic_s");
             list.add(news);
            }
            if(list!=null){
                Myxlv ad=new Myxlv(MainActivity.this,list);
                xlv.setAdapter(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}