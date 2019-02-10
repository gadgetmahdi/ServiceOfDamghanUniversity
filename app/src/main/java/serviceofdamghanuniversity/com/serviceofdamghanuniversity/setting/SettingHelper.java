package serviceofdamghanuniversity.com.serviceofdamghanuniversity.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SettingHelper {
 // private static final String SettingPreferences = "SettingPreferences";
  private static final String SettingKey = "MapType";
  public static final String GoogleMap = "GoogleMap";
  public static final String OpenStreetMap = "OpenStreetMap";


  public static void saveSettingData(Context context, String mapType) {
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putString(SettingKey, mapType);
    editor.apply();
  }

  public static String getSettingData(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    String restoredText = prefs.getString(SettingKey, null);
    if (restoredText != null) {
     return prefs.getString(SettingKey, GoogleMap);
    }
    return GoogleMap;
  }
}
