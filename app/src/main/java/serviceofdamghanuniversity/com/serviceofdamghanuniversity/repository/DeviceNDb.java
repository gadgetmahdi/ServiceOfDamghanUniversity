package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DeviceNDb")
public class DeviceNDb {

  @DatabaseField(columnName = "id",id = true)
  private int id;

  @DatabaseField(columnName = "name")
  private String name;

  @DatabaseField(columnName = "category")
  private String category;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
