package com.example.asus.jishi20170830;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

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

import Fragment.Fragment_left;
import Fragment.Fragment_right;
import Fragment.Fragment_top;
import Fragment.Fragment_yule;
import Fragment.Fragment_shehui;
import Fragment.Fragment_guonei;
import Fragment.Fragment_guoji;
import Fragment.Fragment_tiyu;
import Fragment.Fragment_caijing;
import Fragment.Fragment_junshi;
import Fragment.Fragment_keji;
import Fragment.Fragment_shishang;
import Fragment.HorLinearlaout;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {
    @ViewInject(R.id.hor)
    HorLinearlaout hor;
    @ViewInject(R.id.img_icon)
    ImageView  img_icon;
    @ViewInject(R.id.img_more)
    ImageView  img_more;
    private Handler h=new Handler();
    private  List<String> meuns=new ArrayList<>();
    private  List<Fragment> fragmens=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ff0000"));
        }
        x.view().inject(this);
        //setContentView(R.layout.activity_main);
        initview();
        // 初始化控件
        initdata();
        hor.initdraw(meuns);
        hor.initdrawvp(fragmens);
    }

    private void initdata() {
        meuns.add("头条");
        meuns.add("社会");
        meuns.add("国内");
        meuns.add("国际");
        meuns.add("娱乐");
        meuns.add("体育");
        meuns.add("军事");
        meuns.add("科技");
        meuns.add("财经");
        meuns.add("时尚");
        fragmens.add(new Fragment_top());
        fragmens.add(new Fragment_shehui());
        fragmens.add(new Fragment_guonei());
        fragmens.add(new Fragment_guoji());
        fragmens.add(new Fragment_yule());
        fragmens.add(new Fragment_tiyu());
        fragmens.add(new Fragment_junshi());
        fragmens.add(new Fragment_keji());
        fragmens.add(new Fragment_caijing());
        fragmens.add(new Fragment_shishang());
    }
    private void initview() {
        initmenu();
    }

    private void initmenu() {
        final SlidingMenu menu=getSlidingMenu();
        //左fragment布局
        setBehindContentView(R.layout.left_count);
        getSupportFragmentManager().beginTransaction().add(R.id.left_count,new Fragment_left()).commit();
        //右fragment布局
        menu.setSecondaryMenu(R.layout.right_count);
        getSupportFragmentManager().beginTransaction().add(R.id.right_count,new Fragment_right()).commit();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置展示距屏幕多少距离
        menu.setBehindOffset(48);
        //设置渐变效果
        menu.setFadeDegree(0.35f);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   menu.showMenu();
            }
        });
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.showSecondaryMenu();
            }
        });
/*        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setFadeEnabled(true);
         menu.setBehindOffset(100);
        menu.attachToActivity(MainActivity.this,SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(1f);
        menu.setFadeEnabled(true);
        menu.setMenu(R.layout.left_count);*/
    }


}
