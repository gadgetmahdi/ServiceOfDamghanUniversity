package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub;

import android.content.Context;

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
    busDetails.setDetail(context.getString(R.string.bus_details));
    busDetails.setDriverName("peter jackson");
    busDetails.setSpeed(position.getSpeed());
    //busDetails.setBusIsOnline(!position.isOutdated());
    busDetails.setBusLastUpdateTime(position.getDeviceTime());
    busDetails.setLatLng(new LatLng(position.getLatitude(), position.getLongitude()));
    busDetails.setIconId(R.drawable.bus);

    return busDetails;
  }

  private static String findNameById(Context context, int deviceId) {
    DbHelper dbHelper = new DbHelper(context);
    try {
      DeviceNDb deviceNDb = dbHelper.getById(deviceId);
      return deviceNDb.getName();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }

}
