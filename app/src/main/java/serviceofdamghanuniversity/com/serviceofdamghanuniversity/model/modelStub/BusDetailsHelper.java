package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceNDb;

public class BusDetailsHelper {

  public static BusDetails parseBusDetails(Context context, Position position) {
    BusDetails busDetails = new BusDetails();


    int busId = position.getDeviceId();

    busDetails.setBusId(position.getDeviceId());
    busDetails.setName(findNameById(context, busId));

    if (busId == 10) {
      busDetails.setDetail(context.getString(R.string.bus_details));
    } else {
      busDetails.setDetail("");
    }

    busDetails.setDriverName("peter jackson");
    busDetails.setSpeed(position.getSpeed());
    busDetails.setBusStatus(findStatusById(context, busId));
    //busDetails.setBusIsOnline(!position.isOutdated());
    busDetails.setBusLastUpdateTime(position.getDeviceTime());
    busDetails.setLatLng(new LatLng(position.getLatitude(), position.getLongitude()));

    switch (busDetails.getBusStatus()) {
      case "online":
        busDetails.setIconId(busColorWithId(busId));
        break;
      case "offline":
        //busDetails.setIconId(R.drawable.bus_offline);
        busDetails.setIconId(busColorWithId(busId));
        break;
      default:
        busDetails.setIconId(R.drawable.bus_offline);
        break;
    }


    return busDetails;
  }


  private static int busColorWithId(int busId) {
    switch (busId) {
      case 10:
        return R.drawable.bus_green;
      case 11:
        return R.drawable.bus_pink;
      case 12:
        return R.drawable.bus_yellow;
      default:
        return R.drawable.bus_blue;
    }
  }


  private static String findNameById(Context context, int deviceId) {
    DbHelper dbHelper = new DbHelper(context);
    try {
      DeviceNDb deviceNDb = dbHelper.getById(deviceId);
      if (deviceNDb.getName() != null) {
        return deviceNDb.getName();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }


  private static String findStatusById(Context context, int deviceId) {
    DbHelper dbHelper = new DbHelper(context);
    try {
      DeviceNDb deviceNDb = dbHelper.getById(deviceId);
      if (deviceNDb.getStatus() != null) {
        Log.w("mehdiVijeh", "if " + deviceNDb.getStatus());
        return deviceNDb.getStatus();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }

}
