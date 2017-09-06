package com.example.asus.jishi20170830;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import Adapter.Myrelv;
import Bean.Result;
import ShareUtils.Dao;

public class LoadingActivity extends AppCompatActivity {
    private List<Result> list=new ArrayList<>();
    private RecyclerView relv;
    private Button bt_xiazai;
    private Dao dao;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ff0000"));
        }
        dao=new Dao(this);
        bt_xiazai = (Button) findViewById(R.id.bt_xiazai);
        relv = (RecyclerView) findViewById(R.id.relv);
        Result r= new Result();
        r.newstype="top";
         r.newsname="推荐";
        r.status=true;
        list.add(r);
        r= new Result();
        r.newsname="社会";
        r.newstype="shehui";
        r.status=true;
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="国内";
        r.newstype="guonei";
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="国际";
        r.newstype="guoji";
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="娱乐";
        r.newstype="yule";
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="体育";
        r.newstype="tiyu";
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="军事";
        r.newstype="junshi";
        list.add(r);
        r= new Result();
        r.status=true;
        r.newsname="科技";
        r.newstype="keji";
        list.add(r);
        r= new Result();
        r.newsname="财经";
        r.newstype="caijing";
        r.status=true;
        list.add(r);
        r= new Result();
        r.newsname="时尚";
        r.newstype="shishang";
        r.status=true;
        list.add(r);
        Myrelv myrelv=new Myrelv(this,list);
        relv.setLayoutManager(new LinearLayoutManager(this));
        relv.setAdapter(myrelv);
        myrelv.setOnclieck(new Myrelv.Onclieck() {
            @Override
            public void onclieck(int postion, CheckBox view) {
            if(view.isChecked()){
                list.get(postion).status=true;
            }else {
                list.get(postion).status=false;
            }
            }
        });
        bt_xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <list.size() ; i++) {
                    System.out.println("下载"+list.get(i).status);
                    if(list.get(i).status){
                        jiazai(list.get(i).newstype);
                    }
                }
            }
        });
    }
    private void jiazai(final String newstype) {
         RequestParams params = new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("type",newstype);
        params.addBodyParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(LoadingActivity.this, result, Toast.LENGTH_SHORT).show();
                dao.add(newstype,result);
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
}
