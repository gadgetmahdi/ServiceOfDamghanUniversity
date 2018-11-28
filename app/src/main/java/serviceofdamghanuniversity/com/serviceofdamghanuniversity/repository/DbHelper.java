package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DbHelper extends OrmLiteSqliteOpenHelper {

  public static final String DB_NAME = "device.db";
  private static final int DB_VERSION = 1;


  public DbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {

      // Create Table with given table name with columnName
      TableUtils.createTable(connectionSource, DeviceNDb.class);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

  }


  public <T> List<T> getAll(Class<T> clazz) throws SQLException {
    Dao<T, ?> dao = getDao(clazz);
    return dao.queryForAll();
  }

  public DeviceNDb getById(int aId) throws SQLException {
    Dao<DeviceNDb, Object> dao = getDao(DeviceNDb.class);
    return dao.queryForId(aId);
  }


  public <T> Dao.CreateOrUpdateStatus createOrUpdate(T obj) throws SQLException {
    Dao<T, ?> dao = (Dao<T, ?>) getDao(obj.getClass());
    return dao.createOrUpdate(obj);
  }


}
