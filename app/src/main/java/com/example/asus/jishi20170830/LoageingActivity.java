package com.example.asus.jishi20170830;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import ShareUtils.SharePre;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@ContentView(R.layout.activity_loageing)
public class LoageingActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.bt_jinru)
    Button bt_jinru;
    @ViewInject(R.id.ed_code)
    EditText  ed_code;
    @ViewInject(R.id.ed_phone)
    EditText  ed_phone;
    @ViewInject(R.id.tv_getcode)
    TextView tv_getcode;
    private EventHandler eventHandler;
    private Handler h=new Handler();
    private Runnable task;
    private  int time=10;
    private final int  Send=1000;
    private SharedPreferences sp;
    private  SharePre splei=new SharePre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        sp = splei.sharedpre();
        if(sp.getString("phone",null)!=null){
            Toast.makeText(this, "当前用户号码为"+sp.getString("phone",null), Toast.LENGTH_SHORT).show();
        }
        bt_jinru.setOnClickListener(this);
        tv_getcode.setOnClickListener(this);
        task = new Runnable() {
            @Override
            public void run() {
            time--;
             if(time==0){
                 time=10;
                 h.removeCallbacks(this);
                 tv_getcode.setText("重新发送");
                 tv_getcode.setEnabled(true);
             }else {
                 tv_getcode.setText(time+"s");
                 h.postDelayed(this,Send);
             }
            }
        };
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoageingActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoageingActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                String phone = ed_phone.getText().toString();
                                splei.add("phone",phone);
                            }
                        });
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoageingActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.tv_getcode:
                qureyphone();
                break;
            case  R.id.bt_jinru:
                qureycode();
                break;
        }
    }
    public  void qureyphone(){
        if(TextUtils.isEmpty(ed_phone.getText().toString())){
            Toast.makeText(this, "手机号为空！！！", Toast.LENGTH_SHORT).show();
            return;
        }else{
            SMSSDK.getVerificationCode("86",ed_phone.getText().toString());
            h.postDelayed(task,Send);
            tv_getcode.setEnabled(false);
        }
    }
    public  void qureycode(){
        if(TextUtils.isEmpty(ed_code.getText().toString())){
            Toast.makeText(this, "验证码为空！！！", Toast.LENGTH_SHORT).show();
            return;
        }else {
            SMSSDK.submitVerificationCode("86",ed_phone.getText().toString(),ed_code.getText().toString());
        }

    }


}
