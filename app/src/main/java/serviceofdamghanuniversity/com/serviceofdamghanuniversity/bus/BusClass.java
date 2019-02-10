package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.di.component.DaggerBusComponent;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.di.module.BusModule;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.model.EventBusModel;

public class BusClass implements BusContract.BusView {

  private static final String TAG = "BusClass";
  private Context context;

  @Inject
  BusContract.BusPresenter mBusPresenter;

  public BusClass(Context context) {
    this.context = context;

    getBus();
  }

  private void getBus() {

    BusModule busModule = new BusModule(this, context);
    DaggerBusComponent
      .builder()
      .busModule(busModule)
      .build()
      .injectBus(this);

    mBusPresenter.loadBusData();

  }

  @Override
  public void onBusDataLoaded(Throwable throwable) {
    if(throwable == null){
      Log.d(TAG, "onBusDataLoaded: ");
      EventBusModel eventBusModel = new EventBusModel();
      eventBusModel.setBusDataLoaded(true);
      EventBus.getDefault().post(eventBusModel);
    }else {
      Log.d(TAG, "onBusDataLoaded: " + throwable.getMessage());
      EventBusModel eventBusModel = new EventBusModel();
      eventBusModel.setBusDataLoaded(false);
      EventBus.getDefault().post(eventBusModel);
    }
  }
}
