package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class NetworkChangeReceiver extends BroadcastReceiver {

  public static class NetworkEvent {
    private boolean haveInternet;

    public boolean isHaveInternet() {
      return haveInternet;
    }

    public void setHaveInternet(boolean haveInternet) {
      this.haveInternet = haveInternet;
    }
  }

  public NetworkChangeReceiver() {

  }

  @Override
  public void onReceive(final Context context, final Intent intent) {


    int status = NetworkUtil.getConnectivityStatusString(context);
    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
      if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
        NetworkEvent networkEvent = new NetworkEvent();
        networkEvent.setHaveInternet(false);
        EventBus.getDefault().post(networkEvent);
      } else {
        NetworkEvent networkEvent = new NetworkEvent();
        networkEvent.setHaveInternet(true);
        EventBus.getDefault().post(networkEvent);
      }
    }


  }


}
