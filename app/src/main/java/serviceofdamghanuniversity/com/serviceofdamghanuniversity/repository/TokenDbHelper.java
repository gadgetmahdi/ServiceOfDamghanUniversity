package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class TokenDbHelper extends OrmLiteSqliteOpenHelper {

  public static final String DB_NAME = "token.db";
  private static final int DB_VERSION = 1;


  public TokenDbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    getWritableDatabase();
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, TokenDb.class);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

  }


  public <T> Dao.CreateOrUpdateStatus createOrUpdate(TokenDb tokenDb) throws SQLException {
    Dao<TokenDb, ?> dao = (Dao<TokenDb, ?>) getDao(tokenDb.getClass());
    return dao.createOrUpdate(tokenDb);
  }

  public String getToken() throws SQLException {
    Dao<TokenDb, Object> dao = getDao(TokenDb.class);
     if(dao.queryForId(0) != null){
      return dao.queryForId(0).getToken();
    }else {
       return "";
     }
  }


}
