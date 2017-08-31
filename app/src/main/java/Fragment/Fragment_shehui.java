package Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Fragment_shehui extends Fragment {
    private View view;
    private List<News> list=new ArrayList<>();
    private XListView xlv;
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
        RequestParams params=new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("type","shehui");
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
                Myxlv ad=new Myxlv(getActivity(),list);
                xlv.setAdapter(ad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
