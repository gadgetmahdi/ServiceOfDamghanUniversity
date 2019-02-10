package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {

  @SerializedName("id")
  @Expose
  private int id;

  @SerializedName("category")
  @Expose
  private String category;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("status")
  @Expose
  private String status;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
