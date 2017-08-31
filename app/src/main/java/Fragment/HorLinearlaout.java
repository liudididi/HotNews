package Fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.jishi20170830.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Myxlv;

/**
 * Created by asus on 2017/8/30.
 */

public class HorLinearlaout extends LinearLayout implements ViewPager.OnPageChangeListener{
    private  Context context;
    private  int size=0;
    private int offset = 0;
    private int scrollViewWidth = 0;
    private LinearLayout horscroll;
    private List<String> menus;
    private List<Fragment> fragments;
    private View view;
    private ViewPager vp;
    private HorizontalScrollView hsv;

    public HorLinearlaout(Context context) {
        super(context);
        init(context,null);
    }
    public HorLinearlaout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public HorLinearlaout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs){
        this.context=context;
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.HorLinearlaout);
        size=array.getDimensionPixelSize(R.styleable.HorLinearlaout_size,15);
        array.recycle();
        view = View.inflate(context, R.layout.horlinear, this);
        horscroll = view.findViewById(R.id.horscroll);
        hsv = view.findViewById(R.id.hsv);
        vp = view.findViewById(R.id.vp);
    }
  public void initdraw(final List<String> menus){
      this.menus=menus;
      for (int i = 0; i <menus.size(); i++) {
          final TextView tv= (TextView) View.inflate(context,R.layout.tv,null);
          tv.setText(menus.get(i));
          final int finalI = i;
          tv.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view) {
                  vp.setCurrentItem(finalI);
                  if(finalI>=menus.size()/2){
                      scrollViewWidth = hsv.getWidth();
                      hsv.smoothScrollBy(scrollViewWidth/2, 0);
                  }else if(finalI<menus.size()/2){
                      scrollViewWidth = hsv.getWidth();
                      hsv.smoothScrollBy(-scrollViewWidth/2, 0);
                  }
              }
          });
          horscroll.addView(tv);
      }
  }
    public void initdrawvp(List<Fragment> fragments){
        this.fragments=fragments;
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        Myvpage vppage=new Myvpage(fm);
        vp.setAdapter(vppage);
        hsv.setSmoothScrollingEnabled(true);
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (int j = 0; j <menus.size(); j++) {
            TextView ta= (TextView) horscroll.getChildAt(j);
            if(j==position){
                ta.setTextColor(Color.RED);
            }else {
                ta.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        //设置scrollView 滑动
        TextView textView= (TextView) horscroll.getChildAt(position);
        scrollViewWidth = hsv.getWidth();
        if ((scrollViewWidth + offset) < textView.getRight()) {//需要向右移动
            hsv.smoothScrollBy(textView.getRight() - (scrollViewWidth + offset), 0);
            offset += textView.getRight() - (scrollViewWidth + offset);
        }
        if (offset > textView.getLeft()) {//需要向左移动
            hsv.smoothScrollBy(textView.getLeft() - offset, 0);
            offset += textView.getLeft() - offset;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class  Myvpage extends FragmentPagerAdapter{
        public Myvpage(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
