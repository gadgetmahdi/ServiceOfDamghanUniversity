package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {
  @SerializedName("id")
  @Expose
  private int id;

  @SerializedName("description")
  @Expose
  private String description;

  @SerializedName("attribute")
  @Expose
  private String attribute;

  @SerializedName("expression")
  @Expose
  private String expression;

  @SerializedName("type")
  @Expose
  private String type;
}
