package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus;

import javax.inject.Inject;

public class BusClass {

  private static BusClass busClass = null;

  @Inject
  BusContract.BusPresenter mBusPresenter;

  public BusClass newInstance() {
    if (busClass == null)
      return busClass = new BusClass();
    else
      return busClass;
  }

  public void getBus(){
    
  }


}
