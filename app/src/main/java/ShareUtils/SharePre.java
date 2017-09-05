package ShareUtils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.jishi20170830.Appl;

/**
 * Created by asus on 2017/9/1.
 */

public class SharePre {
    private Context context= Appl.context;
    private  final  String name="users";
    private SharedPreferences sp;

    public  SharedPreferences sharedpre(){
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp;
    }
    public  void add(String key,String phone){
        sp.edit().putString(key,phone).commit();
    }

}
