package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.utile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.NetworkUtil;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.EventBusInternetModel;


public class NetworkChangeReceiver extends BroadcastReceiver {


  @Override
  public void onReceive(final Context context, final Intent intent) {


    int status = NetworkUtil.getConnectivityStatusString(context);
    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
      if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
        EventBusInternetModel eventBusInternetModel = new EventBusInternetModel();
        eventBusInternetModel.setConnected(false);
        EventBus.getDefault().post(eventBusInternetModel);
      }else {
        EventBusInternetModel eventBusInternetModel = new EventBusInternetModel();
        eventBusInternetModel.setConnected(true);
        EventBus.getDefault().post(eventBusInternetModel);
      }
    }
  }

}
