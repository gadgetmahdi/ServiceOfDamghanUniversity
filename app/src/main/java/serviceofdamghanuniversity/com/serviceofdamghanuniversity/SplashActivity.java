package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.onesignal.OneSignal;

import java.util.List;

import io.realm.Realm;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;


public class SplashActivity extends Activity implements SaveTokenListener, ResponseListener.Session {

  private TokenClass tokenClass;
  private static final int SPLASH_DISPLAY_LENGTH = 1000;
  private WebServiceCaller webServiceCaller;
  private DeviceDb deviceDb;
  private Realm realm;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);



    deviceDb = new DeviceDb();
    Realm.init(getApplicationContext());
    realm = Realm.getDefaultInstance();
    Log.w("mehdiTest" , deviceDb.getSizeOfDb(realm) + "");

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
        Log.w("MehdiTestV", response.toString() + "");

        List<Devices> devices = response.body();
        if (devices != null) {
          for (Devices device : devices) {
           deviceDb.save(device);
          }
        }

        showSplash();
      }

      @Override
      public void onError(String error) {
        Log.w("MehdiTest191", error + "");

        showSplash();
      }
    });

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
    Log.w("MehdiTest193", "Meh");
    getDevices();
  }

  @Override
  public void onSessionError(String error) {
    Log.w("MehdiTest192", error + "");
    showSplash();
  }
}
