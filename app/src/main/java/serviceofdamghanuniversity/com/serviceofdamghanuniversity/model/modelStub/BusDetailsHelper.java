package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;

public class BusDetailsHelper {

  public static BusDetails parseBusDetails(Context context, Position position) {
    BusDetails busDetails = new BusDetails();

    int busId = position.getDeviceId();

    switch (busId) {
      case 0:
        break;
      case 10:
        busDetails.setBusId(position.getDeviceId());
        busDetails.setName("green bus");
        busDetails.setDetail("only for girls student.");
        busDetails.setDriverName("peter jackson");
        busDetails.setSpeed(position.getSpeed());
        busDetails.setBusIsOnline(!position.isOutdated());
        busDetails.setBusLastUpdateTime(position.getDeviceTime());
        busDetails.setLatLng(new LatLng(position.getLatitude(), position.getLongitude()));
        busDetails.setIconId(R.drawable.ic_bus_green);
        break;
    }

    return busDetails;
  }

}
