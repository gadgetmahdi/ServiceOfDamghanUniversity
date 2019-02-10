package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.presenter;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.BusPositionContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo.BusPositionRepo;

public class BusPositionPresenterImpl implements BusPositionContract.BusPositionPresenter {

  private BusPositionContract.BusPositionView view;
  private BusPositionRepo repo;

  @Inject
  public BusPositionPresenterImpl(BusPositionContract.BusPositionView view, BusPositionRepo repo) {
    this.view = view;
    this.repo = repo;
  }

  @Override
  public void getBusLocation() {
    repo.getBusPosition().subscribe(
      busPositionModel  -> view.onBusLocationReceived(busPositionModel , null),
      throwable ->  view.onBusLocationReceived(null , throwable));
  }
}
