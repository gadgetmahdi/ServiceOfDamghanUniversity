package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;

public class BusPositionRepo {

  private BusPositionNetService api;

  public BusPositionRepo(BusPositionNetService api) {
    this.api = api;
  }

  public Observable<List<Position>> getBusPosition() {
    return api.getBusPosition()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }


}
