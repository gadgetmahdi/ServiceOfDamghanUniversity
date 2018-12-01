package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.PermissionHandler;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class MainActivityN extends PermissionClass implements ResponseListener.Session, LocationListener, BusFragment.BusIdCallBack {


  public interface PositionsForMap {
    void onBusPositionsProvided(ArrayList<Position> listPositions);

    void onMyPositionsProvided(Location location);

    void onBusSelected(int busId);

  }

  public interface PositionsForBuses {
    void onBusPositionsProvided(ArrayList<Position> listPositions);
  }

  public void setOnPosition(PositionsForMap positions) {
    mPositions = positions;
  }

  public void setOnPositionsForBuses(PositionsForBuses positions) {
    mBusPositions = positions;
  }


  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  @BindView(R.id.navigation)
  NavigationView navigation;

  WebServiceCaller webServiceCaller;
  private ArrayList<Position> listPositions = new ArrayList<>();
  private boolean isGetNewPosUpdate = true;
  private boolean permissionReturn;
  private PositionsForMap mPositions;
  private PositionsForBuses mBusPositions;
  private final static int requestInterval = 5000;
  private TokenDbHelper tokenDb;
  private String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
  private ActionBarDrawerToggle toggle;
  private boolean isShowInternetError = false;
  private boolean isPermissionRequestSend = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_n);
    ButterKnife.bind(this);


    toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    checkForUpdate();

    setSupportActionBar(toolbar);


    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      // getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_24dp);
    }


    navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
          case R.id.nav_map:

            viewPager.setCurrentItem(0);
            drawerLayout.closeDrawers();
            return true;
          case R.id.nav_buses:
            viewPager.setCurrentItem(1);
            drawerLayout.closeDrawers();
            return true;
          case R.id.nav_report:
            sendIssue();
            drawerLayout.closeDrawers();
            return true;
          case R.id.nav_check_update:

            if (CheckInternet.isNetworkConnected(MainActivityN.this)) {
              checkForUpdate();
            } else {
              showSnackForInternetConnection();
            }

            drawerLayout.closeDrawers();
            return true;

          default:
            return true;
        }

      }
    });


    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
      ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      new LocationManagerHelper(this, this);
    } else {
      if (!isPermissionRequestSend) {
        if (requestPermission()) {
          new LocationManagerHelper(this, this);
        }
        isPermissionRequestSend = true;
      }
    }



    /*android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }*/

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);


    webServiceCaller = WebServiceCaller.getInstance();


    tokenDb = new TokenDbHelper(this);
    try {
      if (tokenDb.getToken() != null) {

        //Toast.makeText(this, R.string.get_location, Toast.LENGTH_LONG).show();
        // webServiceCaller.createSession(tokenDb.getToken(), this);
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
          @Override
          public void run() {
            if (CheckInternet.isNetworkConnected(MainActivityN.this)) {
              getBusPositions();
            } else {
              if (!isShowInternetError) {
                showSnackForInternetConnection();
                isShowInternetError = true;
              }
            }
            if (isGetNewPosUpdate) {
              mHandler.postDelayed(this, requestInterval);
            }
          }
        };

        mHandler.post(runnable);
      } else {
        TokenClass.getInstance(getApplicationContext(), new SaveTokenListener() {
          @Override
          public void savedToken(String token) {
            try {
              TokenDb tokenDatabase = new TokenDb();
              tokenDatabase.setId(0);
              tokenDatabase.setToken(parseToken(token));
              tokenDb.createOrUpdate(tokenDatabase);
              webServiceCaller.createSession(token, MainActivityN.this);

            } catch (SQLException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void error() {

          }
        });
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private String parseToken(String token) {
    String[] url = token.split("=");
    return url[url.length - 1];
  }


  private void showSnackForInternetConnection() {

    Snackbar.make(drawerLayout, getString(R.string.internet_problem), Snackbar.LENGTH_LONG)
      .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
      .show();

  }

  private void checkForUpdate() {

    final AppUpdater appUpdater = new AppUpdater(MainActivityN.this)
      .setDisplay(Display.DIALOG)
      .setUpdateFrom(UpdateFrom.GITHUB)
      .setGitHubUserAndRepo("gadgetmahdi", "ServiceOfDamghanUniversity")
      .setButtonDoNotShowAgain("")
      .setTitleOnUpdateAvailable(getString(R.string.update_available))
      .setContentOnUpdateAvailable(getString(R.string.update_available_content))
      .setTitleOnUpdateNotAvailable(getString(R.string.update_not_available))
      .setContentOnUpdateNotAvailable(getString(R.string.update_not_available_content))
      .setButtonUpdate(getString(R.string.update_download))
      .setButtonDismiss(getString(R.string.update_cancel))
      .setButtonUpdateClickListener(new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          checkPermissionAndGoToDownload();
        }
      });

    appUpdater.start();
  }

  private void checkPermissionAndGoToDownload() {
    int MyVersion = Build.VERSION.SDK_INT;
    if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
      if (hasReadPermissions() && hasWritePermissions()) {

        downloadUpdateAndGoToInstall();
      } else {
        requestAppPermissions();
        //requestPermissionForWriteExternal();
      }


    } else {
      downloadUpdateAndGoToInstall();
    }
  }


  private void requestPermissionForWriteExternal() {
    ActivityCompat.requestPermissions(MainActivityN.this,
      new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
      10256);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String permissions[], @NonNull int[] grantResults) {
    switch (requestCode) {
      case 12: {
        if (grantResults.length > 0
          && grantResults[0] == PackageManager.PERMISSION_GRANTED
          && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
          downloadUpdateAndGoToInstall();
        }
      }

    }
  }

  private void requestAppPermissions() {
    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      return;
    }

    if (hasReadPermissions() && hasWritePermissions()) {
      return;
    }

    ActivityCompat.requestPermissions(this,
      new String[]{
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
      }, 12); // your request code
  }

  private boolean hasReadPermissions() {
    return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
  }

  private boolean hasWritePermissions() {
    return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
  }

 /* private void downloadUpdateAndGoToInstall(){
    UpdateApp atualizaApp = new UpdateApp();
    atualizaApp.setContext(getApplicationContext());
    String url = this.getString(R.string.update_app_url);
    atualizaApp.execute(url);
  }*/

  private void downloadUpdateAndGoToInstall() {
    try {
      final Uri uri;
      //get url of app on server
      String url = this.getString(R.string.update_app_url);

      if (Build.VERSION.SDK_INT >= 24) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      } else {

        // Toast.makeText(this, getString(R.string.start_download_update), Toast.LENGTH_SHORT).show();
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String fileName = "App.apk";
        destination += fileName;

        final File file = new File(destination);


        if (file.exists())
          file.delete();



        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(getString(R.string.start_download_title));
        request.setTitle(this.getString(R.string.app_name));

        //final Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider" , file);
        //final Uri uri = Uri.parse("file://" + destination);


        uri = Uri.parse("file://" + destination);
        request.setDestinationUri(uri);


        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);


        //set BroadcastReceiver to install app when .apk is downloaded
        final String finalDestination = destination;
        BroadcastReceiver onComplete = new BroadcastReceiver() {
          public void onReceive(Context ctxt, Intent intent) {
         /* Intent intentN = new Intent(Intent.ACTION_VIEW);
          intentN.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId));
          intent.setData(Uri.parse("package:" + getPackageName()));
          intentN.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
          unregisterReceiver(this);*/


            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.fromFile(new File(finalDestination)), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            startActivity(i);


            unregisterReceiver(this);

          }
        };
        //register receiver for when .apk download is compete
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
      }


    } catch (Exception e) {
      Log.w("mehdiTest", e.getMessage() + "");
    }
  }

  private void sendIssue() {
    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    emailIntent.setType("plain/text");

    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"damghanservice@gmail.com"});
    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.report_a_problem));
    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.txt_late_service);
    emailIntent.setPackage("com.google.android.gm");
    if (emailIntent.resolveActivity(getPackageManager()) != null)
      startActivity(Intent.createChooser(emailIntent, getString(R.string.report_a_problem)));
    else
      Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

  }


  private void setupViewPager(ViewPager viewPager) {
    MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
    BusFragment busFragment = new BusFragment();
    busFragment.setBusIdCallBack(this);
    adapter.addFragment(new MapFragment(), getString(R.string.map));
    adapter.addFragment(busFragment, getString(R.string.buses));
    viewPager.setAdapter(adapter);
  }


  public boolean requestPermission() {

    PermissionHandler.OnPermissionResponse permissionHandler = new PermissionHandler.OnPermissionResponse() {
      @Override
      public void onPermissionGranted() {
        permissionReturn = true;
      }

      @Override
      public void onPermissionDenied() {
        permissionReturn = false;
      }
    };

    new PermissionHandler().checkPermission(MainActivityN.this, permissions, permissionHandler);

    return permissionReturn;
  }


  public void getBusPositions() {
    webServiceCaller.getAllJson("/api/positions", new ResponseListener.JsonResponse() {


      @Override
      public void onResponseJson(Response<List<Position>> response) {

        List<Position> positions = response.body();
        listPositions.clear();

        if (positions != null) {
          listPositions.addAll(positions);

          if (mPositions != null) {
            mPositions.onBusPositionsProvided(listPositions);
          }

          if (mBusPositions != null) {
            mBusPositions.onBusPositionsProvided(listPositions);
          }

        } else {
          isGetNewPosUpdate = false;
          Toast.makeText(MainActivityN.this, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
      }


      @Override
      public void onError(String error) {
        Toast.makeText(MainActivityN.this, R.string.server_err, Toast.LENGTH_LONG).show();
        isGetNewPosUpdate = false;
      }
    });
  }


  @Override
  public void onSessionCreated() {
    final Handler mHandler = new Handler();

    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        getBusPositions();
        if (isGetNewPosUpdate) {
          mHandler.postDelayed(this, requestInterval);
        }
      }
    };

    mHandler.post(runnable);
  }

  @Override
  public void onSessionError(String error) {
    Toast.makeText(this, R.string.server_not_fund, Toast.LENGTH_SHORT).show();

  }


  @Override
  public void onBusSelected(int busId) {
    if (mPositions != null) {
      viewPager.setCurrentItem(0);
      mPositions.onBusSelected(busId);
    }
  }


  @Override
  public void myLocationCallback(Location location) {
    if (location != null && mPositions != null) {
      mPositions.onMyPositionsProvided(location);
    }
  }


}


