package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Application;
import android.content.Intent;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;


public class ApplicationClass extends Application implements SaveTokenListener {

  private TokenClass tokenClass;

  @Override
  public void onCreate() {
    super.onCreate();

    tokenClass = TokenClass.getInstance(getApplicationContext(), this);
    tokenClass.createNewTokenIfIsNotExist();
  }


  @Override
  public void savedToken() {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public void error() {
    tokenClass.createNewTokenIfIsNotExist();
  }
}
