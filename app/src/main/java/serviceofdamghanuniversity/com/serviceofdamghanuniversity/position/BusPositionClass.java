package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.CheckInternet;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.model.EventBusLocationModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.di.component.DaggerBusPositionComponent;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.di.module.BusPositionModule;

public class BusPositionClass implements BusPositionContract.BusPositionView {
  @Inject
  BusPositionContract.BusPositionPresenter presenter;

  private static final String TAG = "BusPositionClass";
  private final static int requestInterval = 5000;

  public BusPositionClass(Context context) {

    BusPositionModule busPositionModule = new BusPositionModule(this, context);
    DaggerBusPositionComponent
      .builder()
      .busPositionModule(busPositionModule)
      .build()
      .inject(this);


    final Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        if (CheckInternet.isNetworkConnected(context)) {
          loadBusPos();
          Log.d(TAG, "run: ");
        }
        mHandler.postDelayed(this, requestInterval);
      }
    };

    mHandler.post(runnable);
  }

  private void loadBusPos() {
    presenter.getBusLocation();
  }


  @Override
  public void onBusLocationReceived(List<Position> model, Throwable throwable) {
    if (throwable == null) {
      if (model.size() > 0) {
        EventBusLocationModel locationModel = new EventBusLocationModel();
        locationModel.setListPositions(model);
        EventBus.getDefault().post(locationModel);
      }
    }
  }
}
