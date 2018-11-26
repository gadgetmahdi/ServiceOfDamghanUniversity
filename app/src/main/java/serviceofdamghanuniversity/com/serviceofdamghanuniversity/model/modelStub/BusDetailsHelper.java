package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import io.realm.Realm;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceDb;

public class BusDetailsHelper {

  public static BusDetails parseBusDetails(Context context, Position position) {
    BusDetails busDetails = new BusDetails();


    int busId = position.getDeviceId();


    busDetails.setBusId(position.getDeviceId());
    busDetails.setName(findNameById(context, busId));
    busDetails.setDetail("only for girls student.");
    busDetails.setDriverName("peter jackson");
    busDetails.setSpeed(position.getSpeed());
    //busDetails.setBusIsOnline(!position.isOutdated());
    busDetails.setBusLastUpdateTime(position.getDeviceTime());
    busDetails.setLatLng(new LatLng(position.getLatitude(), position.getLongitude()));
    busDetails.setIconId(R.drawable.ic_bus_green);

    return busDetails;
  }

  private static String findNameById(Context context, int deviceId) {
    DeviceDb deviceDb = new DeviceDb();
    Realm.init(context);
    Realm realm = Realm.getDefaultInstance();
    Log.w("MehdiTest19", deviceId + "");

    if (deviceDb.getRowWithId(realm, deviceId) != null) {
      Log.w("MehdiTest19", deviceId + "");
      return deviceDb.getRowWithId(realm, deviceId).getName();
    } else {
      Log.w("MehdiTest19",  "null");
      return "";
    }


  }

}
