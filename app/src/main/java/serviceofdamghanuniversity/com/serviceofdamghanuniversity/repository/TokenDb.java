package serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class TokenDb {

  private static final String PREFS_NAME = "Token";
  private static final String PREFS_INITIALIZED = "initialized";
  private static final String PREFS_TOKEN = "token";
  private SharedPreferences prefs;

  public TokenDb(Context context) {
    prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
  }

  public boolean checkIsShCreated() {
    return prefs.contains(PREFS_INITIALIZED);
  }

  public void saveToken(String token) {
    if (!checkIsShCreated()) {
      SharedPreferences.Editor shEditor = prefs.edit();
      shEditor.putBoolean(PREFS_INITIALIZED, true);
      shEditor.putString(PREFS_TOKEN, token);
      shEditor.apply();
    }
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

}
