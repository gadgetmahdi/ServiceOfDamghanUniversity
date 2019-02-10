package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;

public class BusRepo {

  private BusNetService api;

  public BusRepo(BusNetService api) {
    this.api = api;
  }

  public Observable<List<Devices>> getBusData() {
    return api.getDevices()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }
}
