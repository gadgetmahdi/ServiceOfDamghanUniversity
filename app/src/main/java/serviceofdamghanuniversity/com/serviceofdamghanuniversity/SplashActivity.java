package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;


import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceNDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;


public class SplashActivity extends Activity implements SaveTokenListener, ResponseListener.Session {

  private TokenClass tokenClass;
  private static final int SPLASH_DISPLAY_LENGTH = 1000;
  private WebServiceCaller webServiceCaller;
  private DbHelper dbHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);


    dbHelper = new DbHelper(this);


    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    tokenClass = TokenClass.getInstance(getApplicationContext(), this);

    TokenDb tokenDb = new TokenDb(this);
    webServiceCaller = WebServiceCaller.getInstance();

    //webServiceCaller.createSession(tokenDb.getToken(), this);

    if (tokenClass.createNewTokenIfIsNotExist()) {

      webServiceCaller.createSession(tokenDb.getToken(), this);
    }


  }


  @Override
  public void savedToken(String token) {


    webServiceCaller.createSession(parseToken(token), this);

  }

  private String parseToken(String token) {
    String[] url = token.split("=");
    return url[url.length - 1];
  }

  public void getDevices() {
    webServiceCaller.getDevices(new ResponseListener.DeviceResponse() {

      @Override
      public void onResponseDevice(Response<List<Devices>> response) {

        List<Devices> devices = response.body();
        if (devices != null) {
          for (Devices device : devices) {
            // deviceDb.save(device);
            saveDeviceOnDb(device);
          }
        }

        showSplash();
      }

      @Override
      public void onError(String error) {
        showSplash();
      }
    });

  }


  private void saveDeviceOnDb(Devices device) {
    DeviceNDb deviceNDb = new DeviceNDb();
    deviceNDb.setId(device.getId());
    deviceNDb.setName(device.getName());
    deviceNDb.setCategory(device.getCategory());

    try {
      dbHelper.createOrUpdate(deviceNDb);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void error() {
    tokenClass.createNewTokenIfIsNotExist();
  }


  private void showSplash() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        openMainActivity();
      }
    }, SPLASH_DISPLAY_LENGTH);

  }

  private void openMainActivity() {
    Intent intent = new Intent(getApplicationContext(), MainActivityN.class);
    startActivity(intent);
    finish();
  }


  @Override
  public void onSessionCreated() {
    getDevices();
  }

  @Override
  public void onSessionError(String error) {
    showSplash();
  }
}
