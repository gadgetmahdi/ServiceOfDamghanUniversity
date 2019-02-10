package serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.BusClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.model.EventBusModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.EventBusInternetModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog.DialogNoInternet;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.utile.NetworkChangeReceiver;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.SessionClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.model.EventBusSessionModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.SplashContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.model.EventBusTokenModel;

public class SplashPresenterImpl implements SplashContract.SplashPresenter {

  private static final String TAG = "SplashPresenterImpl";
  private Context context;
  private SplashContract.SplashView view;
  private BroadcastReceiver networkChangeReceiver;
  private static TokenDbHelper tokenDb;
  private String mToken = null;
  private boolean isCreatedLoad = false;

  @Inject
  public SplashPresenterImpl(Context context, SplashContract.SplashView view) {
    this.context = context;
    this.view = view;
    tokenDb = new TokenDbHelper(context);

    EventBus.getDefault().register(this);

  }

  @Override
  public void loadNeededData() {
    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    context.registerReceiver(networkChangeReceiver = new NetworkChangeReceiver(), intentFilter);
  }

  private void loadData(){
    if (!getTokenFromDb()) {
      new TokenClass(context);
      Log.d(TAG, "loadNeededData: get");
    } else {
      new SessionClass(context, mToken);
      Log.d(TAG, "loadNeededData: load");
    }
  }

  private boolean getTokenFromDb() {
    try {
      if (!tokenDb.getToken().equals("")) {
        mToken = tokenDb.getToken();
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    mToken = null;
    return false;
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void internetStatus(EventBusInternetModel eventBusInternetModel) {
    if (eventBusInternetModel.isConnected()) {
      Log.d(TAG, "internetStatus: ");
      DialogNoInternet.dismissDialog();
      if(!isCreatedLoad) {
        loadData();
        isCreatedLoad = true;
      }
    } else {
      DialogNoInternet.showIsNotConnection(context, networkChangeReceiver);
      isCreatedLoad = false;
    }
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onTokenLoaded(EventBusTokenModel eventBusTokenModel) {
    if (eventBusTokenModel.isTokenLoaded()) {
      new SessionClass(context, eventBusTokenModel.getToken());
      //new SessionClass(context, "eeRod37DdsGE09lNjqZGASPgjm9BlBwp");
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSessionCreated(EventBusSessionModel eventBusSessionModel) {
    if (eventBusSessionModel.isSessionCreated()) {
      new BusClass(context);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBusDataLoaded(EventBusModel eventBusModel) {
    if (eventBusModel.isBusDataLoaded()) {
      view.onDataLoaded();
    }
  }


  @Override
  public void onDestroy() {
    context.unregisterReceiver(networkChangeReceiver);
    EventBus.getDefault().unregister(this);
  }
}
