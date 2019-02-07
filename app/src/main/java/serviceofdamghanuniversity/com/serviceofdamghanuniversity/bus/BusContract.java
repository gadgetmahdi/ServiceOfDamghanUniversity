package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus;

public class BusContract {
  public interface BusPresenter{
    void loadBusData();
  }

  public interface BusView{
    void onBusDataLoaded();
  }
}
