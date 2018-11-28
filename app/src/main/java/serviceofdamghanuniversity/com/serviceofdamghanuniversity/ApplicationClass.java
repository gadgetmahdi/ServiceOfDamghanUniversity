package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

    initOneSignal();
  }

  private void initOneSignal(){
    // OneSignal Initialization
    OneSignal.startInit(this)
      .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
      .unsubscribeWhenNotificationsAreDisabled(true)
      .init();
  }

}
