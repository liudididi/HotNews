package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.jishi20170830.R;

import java.util.List;

import Bean.Result;

/**
 * Created by asus on 2017/9/5.
 */

public  class  Myrelv extends RecyclerView.Adapter<Myrelv.Viewholder>{
    private  Onclieck onclieck;
    private Context context;
    private List<Result> list;
    public Myrelv(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.mrelv, null);
        Viewholder viewholder=new Viewholder(inflate );
        viewholder.ck_xiazai=inflate.findViewById(R.id.ck_xiazai);
        viewholder.tv_xiazai=inflate.findViewById(R.id.tv_xiazai);
        return viewholder;
    }
    @Override
    public void onBindViewHolder(final Viewholder holder, final int position) {
        holder.tv_xiazai.setText(list.get(position).newsname);
        holder.ck_xiazai.setChecked(list.get(position).status);
        holder.ck_xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclieck.onclieck(position,holder.ck_xiazai);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public  interface Onclieck{
        void  onclieck(int postion,CheckBox view);
    }
    class Viewholder extends RecyclerView.ViewHolder{
        TextView tv_xiazai;
        CheckBox ck_xiazai;
        public Viewholder(View itemView) {
            super(itemView);
        }
    }
 public  void  setOnclieck(Onclieck onclieck){
     this.onclieck=onclieck;
 }
}

