package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

/**
 * create with mahdi gadget & mehdi vj 11/2018
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

  WebServiceCaller webServiceCaller;
  GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //object sakhtan az class webServiceCaller
    webServiceCaller = WebServiceCaller.getInstance(this);

    //tarif kardane map
    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    if (supportMapFragment != null)
      supportMapFragment.getMapAsync(this);
  }

  /**
   * gereftane json ha va kar bar roye an ha dar activity
   */
  public void getAlljson() {
    webServiceCaller.getAllJson("/api/positions" , new ResponseListener.JsonResponse() {


      @Override
      public void onResponseJson(Response response) {

      }

      @Override
      public void onError(String error) {

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

    //mokhtasate chahar rahe self daneshghahe damghan
    LatLng latLng = new LatLng(36.168976, 54.322925);

    //mokhtasate noqte shoroe map ro moshakhas mikonad
    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    //mishakhas kardane 1 noqte roye map
    MarkerOptions markerOptions = new MarkerOptions().title("metivj.title")
      .snippet("metivj.snippet").position(latLng);/*.icon(getResources().getDrawable(R.drawable.bus));*/
    map.addMarker(markerOptions);

    //modele map ro moshakhas mikonad
    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


  }
}
