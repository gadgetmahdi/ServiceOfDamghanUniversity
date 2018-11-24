package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

public class BusDetails {

  private String name;
  private String detail;
  private BitmapDescriptor icon;
  private String driverName;
  private int speed;
  private LatLng latLng;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public BitmapDescriptor getIcon() {
    return icon;
  }

  public void setIcon(BitmapDescriptor icon) {
    this.icon = icon;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public LatLng getLatLng() {
    return latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }
}
