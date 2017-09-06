package Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jishi20170830.LoageingActivity;
import com.example.asus.jishi20170830.MainActivity;
import com.example.asus.jishi20170830.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.x;

import java.util.Map;

import ShareUtils.SharePre;

import static android.support.v7.app.AppCompatDelegate.*;
import static com.example.asus.jishi20170830.R.id.img_icon;

/**
 * Created by asus on 2017/8/30.
 */

public class Fragment_left extends Fragment {
    private View view;
    private Button bt_denglu;
    private  ImageView img_qq;
    private SharedPreferences sp;
    private SharePre splei=new SharePre();
    private ImageView img_yue;
    private TextView tv_yue;
    private ImageView img_q;
    private RelativeLayout re_tou;
    private TextView tv_q;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frag_left, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        if(sp.getBoolean("retou",false)){
            re_tou.setVisibility(View.INVISIBLE);
            DisplayImageOptions option=new DisplayImageOptions.Builder()
                    .displayer(new RoundedBitmapDisplayer(120))
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(sp.getString("imgq",null),img_q,option);
            tv_q.setText(sp.getString("tvq",null));

        }else {
            re_tou.setVisibility(View.VISIBLE);
        }
        if(sp.getBoolean("night",false)){
            tv_yue.setText("白天");
        }else {
            tv_yue.setText("夜间");
        }
        initonclick();

    }
    private void initonclick() {
        bt_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(), LoageingActivity.class);
                startActivity(in);
            }
        });
        img_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
            }
        });
        img_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp.getBoolean("night",false)){
                    tv_yue.setText("白天");
                    ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sp.edit().putBoolean("night",false).commit();

                }else {
                    tv_yue.setText("夜间");
                    ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sp.edit().putBoolean("night",true).commit();
                }

            }
        });
    }
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
            Toast.makeText(getActivity(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String conutl=map.get("iconurl");
            String name= map.get("name");
            tv_q.setText(name);
            // x.image().bind(img_qq,conutl);
            sp.edit().putBoolean("retou",true).commit();
            sp.edit().putString("imgq",conutl).commit();
            sp.edit().putString("tvq",name).commit();
            View inflate = View.inflate(getActivity(), R.layout.activity_main, null);
            ImageView img_user=inflate.findViewById(R.id.img_icon);
            x.image().bind(img_user,conutl);
            DisplayImageOptions option=new DisplayImageOptions.Builder()
                    .displayer(new RoundedBitmapDisplayer(120))
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(conutl,img_q,option);
            re_tou.setVisibility(View.INVISIBLE);
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ)) {
                Toast.makeText(getActivity(), "Authorize fail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "no install QQ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getActivity(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    private void initview() {
        bt_denglu = view.findViewById(R.id.bt_denglu);
        img_qq = view.findViewById(R.id.img_qq);
        img_yue = view.findViewById(R.id.img_yue);
        tv_yue = view.findViewById(R.id.tv_yue);
        tv_q = view.findViewById(R.id.tv_q);
        img_q = view.findViewById(R.id.img_q);
        re_tou = view.findViewById(R.id.re_tou);
        sp=splei.sharedpre();
    }
}
