package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub;


import com.google.android.gms.maps.model.LatLng;

public class BusDetails {

  private int busId;
  private boolean busIsOnline;
  private String busLastUpdateTime;
  private String name;
  private String detail;
  private int iconId;
  private String driverName;
  private double speed;
  private LatLng latLng;

  public String getBusLastUpdateTime() {
    return busLastUpdateTime;
  }

  public void setBusLastUpdateTime(String busLastUpdateTime) {
    this.busLastUpdateTime = busLastUpdateTime;
  }

  public boolean isBusIsOnline() {
    return busIsOnline;
  }

  public void setBusIsOnline(boolean busIsOnline) {
    this.busIsOnline = busIsOnline;
  }

  public int getBusId() {
    return busId;
  }

  public void setBusId(int busId) {
    this.busId = busId;
  }

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

  public int getIconId() {
    return iconId;
  }

  public void setIconId(int iconId) {
    this.iconId = iconId;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public LatLng getLatLng() {
    return latLng;
  }

  public void setLatLng(LatLng latLng) {
    this.latLng = latLng;
  }
}
