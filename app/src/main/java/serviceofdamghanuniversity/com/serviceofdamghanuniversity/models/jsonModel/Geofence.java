package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geofence {

  @SerializedName("id")
  @Expose
  private int id;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("description")
  @Expose
  private String description;

  @SerializedName("area")
  @Expose
  private String area;

  @SerializedName("calendarId")
  @Expose
  private int calendarId;

  @SerializedName("attributes")
  @Expose
  private Attributes attributes;

}
