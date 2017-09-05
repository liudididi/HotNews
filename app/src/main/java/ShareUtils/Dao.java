package ShareUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asus on 2017/9/5.
 */

public class Dao  {

    private final Mysql my;

    public  Dao(Context context){
        my = new Mysql(context);
    }
  public  void   add(String newstype,String result){
      SQLiteDatabase db = my.getWritableDatabase();
      db.execSQL("insert into db(newstype,newsresult) values (?,?)",new String[]{newstype,result});
      db.close();
  }
}
