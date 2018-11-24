package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;


public class SplashActivity extends AppCompatActivity implements SaveTokenListener {

  private TokenClass tokenClass;
  private final int SPLASH_DISPLAY_LENGTH = 3000;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    tokenClass = TokenClass.getInstance(getApplicationContext(), this);

    if (tokenClass.createNewTokenIfIsNotExist()) {

      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          openMainActivity();
        }
      }, SPLASH_DISPLAY_LENGTH);

    }

  }

  @Override
  public void savedToken() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        openMainActivity();
      }
    }, SPLASH_DISPLAY_LENGTH);

  }

  @Override
  public void error() {
    tokenClass.createNewTokenIfIsNotExist();
  }


  private void openMainActivity() {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
    finish();
  }
}
