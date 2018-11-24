package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.PermissionHandler;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;


public class SplashActivity extends AppCompatActivity implements SaveTokenListener {

  private TokenClass tokenClass;
  private static final int SPLASH_DISPLAY_LENGTH = 3000;
  private String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

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
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Intent intent = new Intent("PERMISSION_RECEIVER");
    intent.putExtra("requestCode", requestCode);
    intent.putExtra("permissions", permissions);
    intent.putExtra("grantResults", grantResults);
    sendBroadcast(intent);
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
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
    finish();
  }
}
