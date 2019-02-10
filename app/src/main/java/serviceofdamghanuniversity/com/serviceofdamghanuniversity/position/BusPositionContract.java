package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position;

import java.util.List;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;


public class BusPositionContract {
  public interface BusPositionPresenter {
    void getBusLocation();
  }

  public interface BusPositionView {
    void onBusLocationReceived(List<Position> model , Throwable throwable);
  }

}
