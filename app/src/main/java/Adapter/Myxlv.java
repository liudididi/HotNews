package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.jishi20170830.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import Bean.News;

/**
 * Created by asus on 2017/8/30.
 */

public class Myxlv extends BaseAdapter {
    private Context context;
    private List<News> list;
    private  final  int a=0;
    private  final  int b=1;
    private  final  int num=2;

    public Myxlv(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return a;
        }else {
            return b;
        }
    }

    @Override
    public int getViewTypeCount() {
        return num;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int type = getItemViewType(i);
        viewholder1 holder1=null;
        viewholder2 holder2=null;
        if(view==null){
            switch (type){
                case a:
                    view=View.inflate(context, R.layout.xlv_a,null);
                    holder1=new viewholder1();
                    holder1.tv_title1=view.findViewById(R.id.tv_title1);
                    holder1.tv_date1=view.findViewById(R.id.tv_data1);
                    holder1.img_tu1=view.findViewById(R.id.img_tu1);
                    view.setTag(holder1);
                    break;
                case b:
                    view=View.inflate(context, R.layout.xlv_b,null);
                    holder2=new viewholder2();
                    holder2.tv_title2=view.findViewById(R.id.tv_title2);
                    holder2.tv_date2=view.findViewById(R.id.tv_date2);
                    holder2.img_tu2=view.findViewById(R.id.img_tu2);
                    view.setTag(holder2);
                    break;
            }
        }else {
            switch (type){
                case a:
                   holder1= (viewholder1) view.getTag();
                    break;
                case b:
                    holder2= (viewholder2) view.getTag();
                    break;
            }
        }
        switch (type){
            case a:
                holder1.tv_title1.setText(list.get(i).title);
                holder1.tv_date1.setText(list.get(i).date);
                ImageLoader.getInstance().displayImage(list.get(i).thumbnail_pic_s,holder1.img_tu1);
                break;
            case b:
                holder2.tv_title2.setText(list.get(i).title);
                holder2.tv_date2.setText(list.get(i).date);
                ImageLoader.getInstance().displayImage(list.get(i).thumbnail_pic_s,holder2.img_tu2);
                break;
        }

        return view;

    }
    class  viewholder1{
      public   TextView tv_title1;
      public   TextView tv_date1;
         public  ImageView img_tu1;
    }
    class  viewholder2{
        public   TextView tv_title2;
        public   TextView tv_date2;
        public  ImageView img_tu2;
    }
}
