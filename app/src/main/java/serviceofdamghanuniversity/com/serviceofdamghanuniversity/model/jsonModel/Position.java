package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Position {
  @SerializedName("id")
  @Expose
  private int id;

 /* @SerializedName("attributes")
  @Expose
  private Attributes attributes;
  */

  @SerializedName("deviceId")
  @Expose
  private int deviceId;

  @SerializedName("protocol")
  @Expose
  private String protocol;

  @SerializedName("serverTime")
  @Expose
  private String serverTime;

  @SerializedName("deviceTime")
  @Expose
  private String deviceTime;

  @SerializedName("fixTime")
  @Expose
  private String fixTime;

  @SerializedName("outdated")
  @Expose
  private boolean outdated;

  @SerializedName("valid")
  @Expose
  private boolean valid;

  @SerializedName("latitude")
  @Expose
  private double latitude;

  @SerializedName("longitude")
  @Expose
  private double longitude;

  @SerializedName("altitude")
  @Expose
  private int altitude;

  @SerializedName("speed")
  @Expose
  private double speed;

  /*@SerializedName("course")
  @Expose
  private int course;*/

  @SerializedName("address")
  @Expose
  private String address;

  @SerializedName("accuracy")
  @Expose
  private int accuracy;

  /*@SerializedName("network")
  @Expose
  private String network;*/


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

  public String getServerTime() {
    return serverTime;
  }

  public void setServerTime(String serverTime) {
    this.serverTime = serverTime;
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

  public boolean isOutdated() {
    return outdated;
  }

  public void setOutdated(boolean outdated) {
    this.outdated = outdated;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public int getAltitude() {
    return altitude;
  }

  public void setAltitude(int altitude) {
    this.altitude = altitude;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

 /* public int getCourse() {
    return course;
  }

  public void setCourse(int course) {
    this.course = course;
  }*/

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
