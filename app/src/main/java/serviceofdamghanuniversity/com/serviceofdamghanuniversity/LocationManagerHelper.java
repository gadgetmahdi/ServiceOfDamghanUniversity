package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;


public class LocationManagerHelper implements android.location.LocationListener {

  private android.location.LocationManager locationManager;
  private final static long MIN_TIME_BW_UPDATES = 2000;
  private final static float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
  private MainActivityN activity;
  private boolean isGpsEnabled, isNetworkEnabled;
  private LocationListener myLocationListener;

  public LocationManagerHelper(MainActivityN activity , LocationListener myLocationListener) {
    this.activity = activity;
    this.myLocationListener = myLocationListener;

    locationManager = createLocationManagerForGetUserLocation();

    checkProviderIsEnable();

    createListenerForGpsOrNet();
  }

  private android.location.LocationManager createLocationManagerForGetUserLocation() {
    return (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
  }

  private void checkProviderIsEnable() {
    isGpsEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
  }

  private void createListenerForGpsOrNet() {
    if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isGpsEnabled) {
        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
      } else if (isNetworkEnabled) {
        locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
      } else {
        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
      }
    }
  }


  @Override
  public void onLocationChanged(Location location) {
    myLocationListener.myLocationCallback(location);
  }

  @Override
  public void onStatusChanged(String s, int i, Bundle bundle) {

  }

  @Override
  public void onProviderEnabled(String s) {

  }

  @Override
  public void onProviderDisabled(String s) {

  }
}
