package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;


import android.support.annotation.NonNull;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;

public class DeviceDb extends RealmObject {

  @PrimaryKey
  private int uid;

  private int id;

  private String name;

  private String category;


  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

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


  public void save(final Devices devices) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(@NonNull Realm bgRealm) {

        if (bgRealm.where(DeviceDb.class).equalTo("id", devices.getId()).findFirst() != null) {
          DeviceDb deviceDb = bgRealm.where(DeviceDb.class).equalTo("id", devices.getId()).findFirst();
          if (deviceDb != null) {
            deviceDb.setId(devices.getId());
            deviceDb.setCategory(devices.getCategory());
            deviceDb.setName(devices.getName());
          }
        }else {
          Number currentIdNum = bgRealm.where(DeviceDb.class).max("id");
          int nextId;
          if (currentIdNum == null) {
            nextId = 1;
          } else {
            nextId = currentIdNum.intValue() + 1;
          }

          DeviceDb deviceDb = bgRealm.createObject(DeviceDb.class , nextId);
          deviceDb.setId(devices.getId());
          deviceDb.setCategory(devices.getCategory());
          deviceDb.setName(devices.getName());
        }
      }
    }, new Realm.Transaction.OnSuccess() {
      @Override
      public void onSuccess() {

      }
    });

  }

  public DeviceDb getRowWithId(Realm bgRealm, long id) {
    return bgRealm.where(DeviceDb.class).equalTo("id", id).findFirst();
  }

  public long getSizeOfDb(Realm realm){
    return realm.where(DeviceDb.class).count();
  }
}
