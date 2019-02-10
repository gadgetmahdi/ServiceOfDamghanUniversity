package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "TokenDb")
public class TokenDb {


  @DatabaseField(columnName = "id",id = true)
  private int id;

  @DatabaseField(columnName = "token")
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  /* private static final String PREFS_NAME = "Token";
  private static final String PREFS_INITIALIZED = "initialized";
  private static final String PREFS_TOKEN = "token";
  private SharedPreferences prefs;

  public TokenDb(Context context) {
    prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
  }

  public boolean checkIsShCreated() {
    return prefs.contains(PREFS_INITIALIZED);
  }

  public boolean checkIsShHaveData() {
    return prefs.contains(PREFS_TOKEN);
  }

  public void saveToken(String token) {
      SharedPreferences.Editor shEditor = prefs.edit();
      shEditor.putBoolean(PREFS_INITIALIZED, true);
      shEditor.putString(PREFS_TOKEN, token);
      shEditor.apply();
  }

  public void deleteToken() {
    if (checkIsShCreated()) {
      SharedPreferences.Editor shEditor = prefs.edit();
      shEditor.putBoolean(PREFS_INITIALIZED, false);
      shEditor.putString(PREFS_TOKEN, null);
      shEditor.apply();
    }
  }

  public String getToken() {
    return prefs.getString(PREFS_TOKEN, null);
  }
*/
}
