package ShareUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by asus on 2017/9/4.
 */

public class ConnectivityUtils {
 private  InterWork interWork;
    public  void getnet(Context context,InterWork interWork){
        ConnectivityManager mConnectivityManager = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isAvailable()) {
            /////////////网络连接
            String name = netInfo.getTypeName();
            if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
                interWork.wifinetwork();
            }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){
                /////有线网络
               interWork.phonenetwork();
            }
        } else {
            interWork.nonetwork();
        }
    }
public  interface InterWork{
    void  wifinetwork();
    void  phonenetwork();
    void  nonetwork();
}
    public void setInterWork(InterWork interWork) {
        this.interWork = interWork;
    }
}
