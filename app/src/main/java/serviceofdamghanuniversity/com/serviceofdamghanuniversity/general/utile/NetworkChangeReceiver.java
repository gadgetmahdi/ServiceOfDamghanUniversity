package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.utile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.NetworkUtil;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog.DialogNoInternet;


public class NetworkChangeReceiver extends BroadcastReceiver {


  @Override
  public void onReceive(final Context context, final Intent intent) {


    int status = NetworkUtil.getConnectivityStatusString(context);
    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
      if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
        DialogNoInternet.showIsNotConnection(context);
      }
    }


  }


}
