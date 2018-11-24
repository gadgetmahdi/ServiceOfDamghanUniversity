package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {

  @SerializedName("distance")
  @Expose
  private int distance;
  @SerializedName("totalDistance")
  @Expose
  private double totalDistance;
  @SerializedName("motion")
  @Expose
  private boolean motion;
  @SerializedName("obdSpeed")
  @Expose
  private int obdSpeed;


  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public double getTotalDistance() {
    return totalDistance;
  }

  public void setTotalDistance(double totalDistance) {
    this.totalDistance = totalDistance;
  }

  public boolean isMotion() {
    return motion;
  }

  public void setMotion(boolean motion) {
    this.motion = motion;
  }

  public int getObdSpeed() {
    return obdSpeed;
  }

  public void setObdSpeed(int obdSpeed) {
    this.obdSpeed = obdSpeed;
  }
}
