package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.onesignal.OneSignal;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.PermissionHandler;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;


public class SplashActivity extends PermissionClass implements SaveTokenListener {

  private TokenClass tokenClass;
  private static final int SPLASH_DISPLAY_LENGTH = 1000;
  private String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true).init();

    tokenClass = TokenClass.getInstance(getApplicationContext(), this);

    if (tokenClass.createNewTokenIfIsNotExist()) {
      showSplash();
    }

  }

  private void requestPermission() {

    PermissionHandler.OnPermissionResponse permissionHandler = new PermissionHandler.OnPermissionResponse() {
      @Override
      public void onPermissionGranted() {
        openMainActivity();
      }

      @Override
      public void onPermissionDenied() {
        openMainActivity();
      }
    };

    new PermissionHandler().checkPermission(SplashActivity.this, permissions, permissionHandler);

  }



  @Override
  public void savedToken() {
    showSplash();
  }

  @Override
  public void error() {
    tokenClass.createNewTokenIfIsNotExist();
  }


  private void showSplash() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        requestPermission();
      }
    }, SPLASH_DISPLAY_LENGTH);

  }

  private void openMainActivity() {
    Intent intent = new Intent(getApplicationContext(), MainActivityN.class);
    startActivity(intent);
    finish();
  }
}
