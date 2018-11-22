package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Application;


public class ApplicationClass extends Application{


  @Override
  public void onCreate() {
    super.onCreate();

    TokenClass tokenClass = TokenClass.getInstance(getApplicationContext());
    tokenClass.createNewTokenIfIsNotExist();
  }


}
