package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.DeviceNDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;


public class SplashActivity extends AppCompatActivity implements SaveTokenListener, ResponseListener.Session {

  private TokenClass tokenClass;
  private static final int SPLASH_DISPLAY_LENGTH = 1000;
  private WebServiceCaller webServiceCaller;
  private DbHelper dbHelper;
  private TokenDbHelper tokenDb;
  private AlertDialog dialog;
  private SplashActivity splashActivity = this;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    StartAnimations();

    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(new NetworkChangeReceiver(), intentFilter);


    dbHelper = new DbHelper(this);

    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    tokenClass = TokenClass.getInstance(getApplicationContext(), this);

    tokenDb = new TokenDbHelper(this);
    webServiceCaller = WebServiceCaller.getInstance();

    //webServiceCaller.createSession(tokenDb.getToken(), this);

    try {
      if (new ServiceManager(this).isNetworkAvailable()) {
        if (tokenClass.createNewTokenIfIsNotExist()) {
          webServiceCaller.createSession(tokenDb.getToken(), this);
        }
      } else {
        showIsNotConnection();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
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
        Devices devices = new Devices();
        devices.setName("");
        devices.setCategory("");
        devices.setId(-1);
        saveDeviceOnDb(devices);
      }
    });

  }


  private void saveDeviceOnDb(Devices device) {
    DeviceNDb deviceNDb = new DeviceNDb();
    deviceNDb.setId(device.getId());
    deviceNDb.setName(device.getName());
    deviceNDb.setCategory(device.getCategory());
    deviceNDb.setStatus(device.getStatus());
    try {
      dbHelper.createOrUpdate(deviceNDb);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void error() {
    try {
      tokenClass.createNewTokenIfIsNotExist();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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

   private void StartAnimations() {
     Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
     anim.reset();
     TextView iv = (TextView) findViewById(R.id.txt_developer);
     iv.clearAnimation();
     iv.startAnimation(anim);

     anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
     anim.reset();
     TextView ib = (TextView) findViewById(R.id.txt_create_by);
     ib.clearAnimation();
     ib.startAnimation(anim);

     anim = AnimationUtils.loadAnimation(this, R.anim.top_to_down);
     anim.reset();
     ImageView im = (ImageView) findViewById(R.id.logo);
     im.clearAnimation();
     im.startAnimation(anim);

     anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
     anim.reset();
     ProgressBar pr = (ProgressBar) findViewById(R.id.progress_bar);
     pr.clearAnimation();
     pr.startAnimation(anim);

   }

  @Override
  public void onSessionCreated() {
    getDevices();
  }

  @Override
  public void onSessionError(String error) {
    //getDevicesClass.saveNullOnDb();
    //showSplash();
    showIsNotConnection();
  }


  private void showIsNotConnection() {
    AlertDialog.Builder builder;
    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Toast.makeText(splashActivity, "yhis", Toast.LENGTH_SHORT).show();
      builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
    } else {
      builder = new AlertDialog.Builder(this);
    }*/

    builder = new AlertDialog.Builder(this);
    builder.setCancelable(false);
    LayoutInflater inflater = LayoutInflater.from(this);
    View view = inflater.inflate(R.layout.custom_dilaog, null);
    final TextView txtTitle = view.findViewById(R.id.txt_title);
    final TextView txtContent = view.findViewById(R.id.txt_content);
    final Button btnExit = view.findViewById(R.id.btn_exit);


    builder.setView(view);
    txtTitle.setText(getString(R.string.no_connection_title));
    txtContent.setText(getString(R.string.no_connection_message));
    btnExit.setText(getString(R.string.no_connection_exit_button));
    btnExit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
      }
    });
    dialog = builder.create();
    dialog.show();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(NetworkChangeReceiver.NetworkEvent event) {
    tryAgainForInternetConnection();
  }

  public void tryAgainForInternetConnection() {
    if (dialog.isShowing())
      dialog.dismiss();
    try {
      if (tokenClass.createNewTokenIfIsNotExist()) {
        webServiceCaller.createSession(tokenDb.getToken(), splashActivity);
      } else {
        webServiceCaller.createSession(tokenDb.getToken(), splashActivity);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}