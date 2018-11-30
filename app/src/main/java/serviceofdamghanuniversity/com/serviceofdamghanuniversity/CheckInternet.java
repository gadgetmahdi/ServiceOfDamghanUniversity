package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.net.ConnectivityManager;


public class CheckInternet {

  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm != null && cm.getActiveNetworkInfo() != null;
  }

}
