package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.presenter;

import android.util.Log;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.BusContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo.BusRepo;

public class BusPresenterImpl implements BusContract.BusPresenter {

  private static final String TAG = "BusPresenterImpl";
  private BusContract.BusView view;
  private BusRepo repo;

  @Inject
  public BusPresenterImpl(BusContract.BusView view, BusRepo repo) {
    this.view = view;
    this.repo = repo;
  }

  @Override
  public void loadBusData() {
    repo.getBusData().subscribe(
      devices -> Log.d(TAG, "loadBusData: ")
      , throwable -> Log.d(TAG, "loadBusData: t"));
  }
}
