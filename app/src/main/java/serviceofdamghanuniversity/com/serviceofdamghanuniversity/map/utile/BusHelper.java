package serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.utile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BusHelper {

  public static long getDiffTime(String timeString) {
    Locale locale = new Locale("fa_ ");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale);
    //
    try {
      Date busTime = df.parse(timeString);
      Date currentTime = new Date();
      return currentTime.getTime() - busTime.getTime();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }


  public  static boolean isDeviceIsOnline(long diff) {
    return !(diff >= 30000);
  }


}
