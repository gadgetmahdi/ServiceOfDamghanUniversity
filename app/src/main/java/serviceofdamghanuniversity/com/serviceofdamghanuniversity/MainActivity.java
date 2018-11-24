package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetailsHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.PermissionHandler;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

/**
 * create with mahdi gadget & mehdi vj 11/2018
 */
public class MainActivity extends PermissionClass implements OnMapReadyCallback, ResponseListener.Session, GoogleMap.OnMapLoadedCallback {

  public final static long MIN_TIME_BW_UPDATES = 2000;
  public final static float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
  WebServiceCaller webServiceCaller;
  GoogleMap map;
  private ArrayList<Position> listPositions = new ArrayList<>();
  private boolean isGetNewPosUpdate = true;
  private final static int requestInterval = 10000;
  private LocationManager locationManager;
  private boolean isSetCameraToMyLocation = false;
  private boolean isPermissionRequestSend = false;
  private String[] permissions = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
  private boolean isGpsEnabled, isNetworkEnabled;
  private FloatingActionButton floatingActionButton;
  private LatLng myLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    floatingActionButton = findViewById(R.id.floatingActionButton);

    // location manager for get user location
    locationManager = createLocationManagerForGetUserLocation();

    checkProviderIsEnable();

    createListenerForGpsOrNet();


    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(myLocation != null){
          //mokhtasate noqte shoroe map ro moshakhas mikonad
          CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(15).build();
          map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

          isSetCameraToMyLocation = true;
        }else {
          Toast.makeText(MainActivity.this,R.string.not_location, Toast.LENGTH_SHORT).show();
        }

      }
    });

    //object sakhtan az class webServiceCaller
    webServiceCaller = WebServiceCaller.getInstance();

    //tarif kardane map
    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    if (supportMapFragment != null)
      supportMapFragment.getMapAsync(this);

    TokenDb tokenDb = new TokenDb(this);
    if (tokenDb.checkIsShHaveData()) {
      Toast.makeText(this,R.string.get_location, Toast.LENGTH_LONG).show();
      webServiceCaller.createSession(tokenDb.getToken(), this);
    }
  }


  private LocationManager createLocationManagerForGetUserLocation() {
    return (LocationManager) getSystemService(Context.LOCATION_SERVICE);
  }


  private void checkProviderIsEnable() {
    isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
  }

  protected void createListenerForGpsOrNet() {
    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isGpsEnabled) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new CheckGpsLocationListener());
      } else if (isNetworkEnabled) {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new CheckGpsLocationListener());
      } else {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new CheckGpsLocationListener());
      }
    } else {
      if (!isPermissionRequestSend) {
        requestPermission();
      }
    }
  }


  private void requestPermission() {
    isPermissionRequestSend = true;

    PermissionHandler.OnPermissionResponse permissionHandler = new PermissionHandler.OnPermissionResponse() {
      @Override
      public void onPermissionGranted() {
        createListenerForGpsOrNet();
      }

      @Override
      public void onPermissionDenied() {
      }
    };

    new PermissionHandler().checkPermission(MainActivity.this, permissions, permissionHandler);

  }

  /**
   * gereftane json ha va kar bar roye an ha dar activity
   */
  public void getBusPositions() {
    webServiceCaller.getAllJson("/api/positions", new ResponseListener.JsonResponse() {


      @Override
      public void onResponseJson(Response<List<Position>> response) {
        //Log.w("mehdiTest" , ((List<Position>)response.body()).get(0).getLatitude()+"");
        List<Position> positions = response.body();

        if (positions != null) {

          listPositions.addAll(positions);

        } else {
          isGetNewPosUpdate = false;
          Toast.makeText(MainActivity.this,R.string.nodata, Toast.LENGTH_SHORT).show();
        }

        map.setOnMapLoadedCallback(MainActivity.this);
      }

      @Override
      public void onError(String error) {
        Toast.makeText(MainActivity.this,R.string.serverـerror, Toast.LENGTH_LONG).show();
        isGetNewPosUpdate = false;
      }
    });
  }

  /**
   * baraye google map va meqdar dehi haye google map dar in qesmat anjam mishavad
   *
   * @param googleMap
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {

    map = googleMap;

    //modele map ro moshakhas mikonad
    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


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
    Toast.makeText(this,R.string.servernotfond, Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onMapLoaded() {
    if (listPositions.size() > 0) {
      for (Position position : listPositions) {

        BusDetails busDetails = BusDetailsHelper.parseBusDetails(this, position);
        LatLng pos = busDetails.getLatLng();
        String name = busDetails.getName();
        String details = busDetails.getDetail();
        String driverName = busDetails.getDriverName();
        BitmapDescriptor icon = busDetails.getIcon();

        if(!isSetCameraToMyLocation) {
          //mokhtasate noqte shoroe map ro moshakhas mikonad
          CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
          map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        //mishakhas kardane 1 noqte roye map
        MarkerOptions markerOptions = new MarkerOptions().title(name)
          .snippet(details).position(pos).icon(icon);
        map.addMarker(markerOptions);
      }
    }
  }


  // inner class for listener of gps
  public class CheckGpsLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {

        myLocation = new LatLng(location.getLatitude(), location.getLongitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  }


}
