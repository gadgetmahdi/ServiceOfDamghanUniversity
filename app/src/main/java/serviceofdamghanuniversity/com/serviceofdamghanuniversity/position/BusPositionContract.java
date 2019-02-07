package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position;

public class BusPositionContract {
  public interface BusPositionPresenter {
    void getBusLocation();
  }

  public interface BusPositionView {
    void onBusLocationReceived();
  }

}
