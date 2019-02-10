package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.presenter;


import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.BusContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo.BusRepo;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceNDb;

public class BusPresenterImpl implements BusContract.BusPresenter {

  private static final String TAG = "BusPresenterImpl";
  private BusContract.BusView view;
  private BusRepo repo;
  private DbHelper dbHelper;

  @Inject
  public BusPresenterImpl(BusContract.BusView view , Context context, BusRepo repo) {
    this.view = view;
    this.repo = repo;

    dbHelper = new DbHelper(context);
  }

  @Override
  public void loadBusData() {
    repo.getBusData().subscribe(
      devices -> {
        if (devices != null) {
          for (Devices device : devices) {
            saveDeviceOnDb(device);
          }
        }else {
          Log.d(TAG, "loadBusData: null");
        }
        view.onBusDataLoaded(null);
      },
      throwable -> view.onBusDataLoaded(throwable));
  }


  private void saveDeviceOnDb(Devices device) {
    Log.d(TAG, "saveDeviceOnDb: " + device.getStatus());
    DeviceNDb deviceNDb = new DeviceNDb();
    deviceNDb.setId(device.getId());
    deviceNDb.setName(device.getName());
    deviceNDb.setCategory(device.getCategory());
    deviceNDb.setStatus(device.getStatus());
    try {
      dbHelper.createOrUpdate(deviceNDb);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
