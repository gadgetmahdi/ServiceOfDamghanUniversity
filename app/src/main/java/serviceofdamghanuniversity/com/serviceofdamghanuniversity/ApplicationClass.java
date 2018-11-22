package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Application;
import android.widget.Toast;


public class ApplicationClass extends Application{


  @Override
  public void onCreate() {
    super.onCreate();

    TokenClass.getInstance(getApplicationContext());
  }


}
