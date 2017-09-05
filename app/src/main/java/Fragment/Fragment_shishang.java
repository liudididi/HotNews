package Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.jishi20170830.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import Adapter.Myxlv;
import Bean.News;
import view.xlistview.XListView;

/**
 * Created by asus on 2017/8/30.
 */

public class Fragment_shishang extends Fragment implements XListView.IXListViewListener{
    private View view;
    private List<News> list=new ArrayList<>();
    private List<News> list1=new ArrayList<>();
    private int start=0;
    private Handler h=new Handler();
    private XListView xlv;
    private Myxlv ad;
    private RequestParams params;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frag_top, null);
        return view ;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv = view.findViewById(R.id.xlv);
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        params = new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("type","shishang");
        params.addBodyParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        //post 请求
         jiazai();
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
                news.author_name=d.optString("author_name");
                news.thumbnail_pic_s=d.optString("thumbnail_pic_s");
                list1.add(news);
            }
            if(list1!=null){
                //添加适配器
                 list=new ArrayList<>();
                 list=getitems(list1);
                 ad = new Myxlv(getActivity(),list);
                 xlv.setAdapter(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<News> getitems(List<News> list1) {
        if(start+17>=list1.size()){
            for (int i = 0; i <list1.size() ; i++) {
                list.add(list1.get(i));
            }
            xlv.setPullLoadEnable(false);
            Toast.makeText(getActivity(), "已经是全部数据了", Toast.LENGTH_SHORT).show();
        }else {
            for (int i = 0; i <start+17 ; i++) {
                list.add(list1.get(i));
            }
        }
        return list;
    }
    public void jiazai(){

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

    @Override
    public void onRefresh() {
     h.postDelayed(new Runnable() {
         @Override
         public void run() {
             start=0;
             xlv.setPullLoadEnable(true);
             list1.clear();
             ad=null;
             jiazai();
             onload();
         }
     }, 2000);
    }

    private void onload() {
        xlv.stopRefresh();
        xlv.stopLoadMore();
        xlv.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                start+=5;
                list=getitems(list1);
                ad.notifyDataSetChanged();
                onload();
            }
        }, 2000);
    }

}
