package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Position {

  @SerializedName("id")
  @Expose
  private int id;

  @SerializedName("deviceId")
  @Expose
  private int deviceId;

  @SerializedName("protocol")
  @Expose
  private String protocol;

  @SerializedName("deviceTime")
  @Expose
  private String deviceTime;

  @SerializedName("fixTime")
  @Expose
  private String fixTime;

  @SerializedName("serverTime")
  @Expose
  private String serverTime;


  @SerializedName("latitude")
  @Expose
  private int latitude;


  @SerializedName("longitude")
  @Expose
  private int longitude;


  @SerializedName("altitude")
  @Expose
  private int altitude;


  @SerializedName("speed")
  @Expose
  private int speed;


  @SerializedName("address")
  @Expose
  private String address;

  @SerializedName("accuracy")
  @Expose
  private int accuracy;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(int deviceId) {
    this.deviceId = deviceId;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getDeviceTime() {
    return deviceTime;
  }

  public void setDeviceTime(String deviceTime) {
    this.deviceTime = deviceTime;
  }

  public String getFixTime() {
    return fixTime;
  }

  public void setFixTime(String fixTime) {
    this.fixTime = fixTime;
  }

  public String getServerTime() {
    return serverTime;
  }

  public void setServerTime(String serverTime) {
    this.serverTime = serverTime;
  }

  public int getLatitude() {
    return latitude;
  }

  public void setLatitude(int latitude) {
    this.latitude = latitude;
  }

  public int getLongitude() {
    return longitude;
  }

  public void setLongitude(int longitude) {
    this.longitude = longitude;
  }

  public int getAltitude() {
    return altitude;
  }

  public void setAltitude(int altitude) {
    this.altitude = altitude;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(int accuracy) {
    this.accuracy = accuracy;
  }
}
