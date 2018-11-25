package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.PermissionHandler;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class MainActivityN extends PermissionClass implements ResponseListener.Session, LocationListener , BusFragment.BusIdCallBack{


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

  WebServiceCaller webServiceCaller;
  private ArrayList<Position> listPositions = new ArrayList<>();
  private boolean isGetNewPosUpdate = true;
  private boolean permissionReturn;
  private PositionsForMap mPositions;
  private PositionsForBuses mBusPositions;
  private final static int requestInterval = 10000;
  private String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_n);
    ButterKnife.bind(this);


    new LocationManagerHelper(this, this);


    



    /*android.support.v7.app.ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }*/

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);


    webServiceCaller = WebServiceCaller.getInstance();


    TokenDb tokenDb = new TokenDb(this);
    if (tokenDb.checkIsShHaveData()) {
      Toast.makeText(this, "please wait until get data from server.", Toast.LENGTH_LONG).show();
      webServiceCaller.createSession(tokenDb.getToken(), this);
    }
  }


  private void setupViewPager(ViewPager viewPager) {
    MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
    BusFragment busFragment = new BusFragment();
    busFragment.setBusIdCallBack(this);
    adapter.addFragment(new MapFragment(), "Map");
    adapter.addFragment(busFragment, "Buses");
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
          }else {
            Toast.makeText(MainActivityN.this, "null", Toast.LENGTH_SHORT).show();
          }

          if (mBusPositions != null) {
            mBusPositions.onBusPositionsProvided(listPositions);
          }else {
            Toast.makeText(MainActivityN.this, "bus null", Toast.LENGTH_SHORT).show();
          }

        } else {
          isGetNewPosUpdate = false;
          Toast.makeText(MainActivityN.this, "no data available.", Toast.LENGTH_SHORT).show();
        }
      }


      @Override
      public void onError(String error) {
        Toast.makeText(MainActivityN.this, "server error." + error, Toast.LENGTH_LONG).show();
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
  public void onError(String error) {
    Toast.makeText(this, "server not respond, " +
      "please try again later.", Toast.LENGTH_SHORT).show();

  }


  @Override
  public void onBusSelected(int busId) {
    if(mPositions != null){
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

