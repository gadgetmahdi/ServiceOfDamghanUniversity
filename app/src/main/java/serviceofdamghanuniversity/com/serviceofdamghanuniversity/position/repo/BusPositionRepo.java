package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.model.BusPositionModel;

public class BusPositionRepo {

  private BusPositionNetService api;

  public BusPositionRepo(BusPositionNetService api) {
    this.api = api;
  }

  public Observable<BusPositionModel> getBusPosition() {
    return api.getBusPosition()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }


}
