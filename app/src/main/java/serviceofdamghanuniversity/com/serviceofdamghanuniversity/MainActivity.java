package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

/**
 * create with mahdi gadget & mehdi vj 11/2018
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ResponseListener.Session, GoogleMap.OnMapLoadedCallback {

  WebServiceCaller webServiceCaller;
  GoogleMap map;
  private ArrayList<Position> listPositions = new ArrayList<>();
  private boolean isGetNewPosUpdate = true;
  private final static int requestInterval = 10000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    //object sakhtan az class webServiceCaller
    webServiceCaller = WebServiceCaller.getInstance();

    //tarif kardane map
    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    if (supportMapFragment != null)
      supportMapFragment.getMapAsync(this);

    TokenDb tokenDb = new TokenDb(this);
    if (tokenDb.checkIsShHaveData()) {
      webServiceCaller.createSession(tokenDb.getToken() , this);
    }
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
          Toast.makeText(MainActivity.this, "no data available.", Toast.LENGTH_SHORT).show();
        }

        map.setOnMapLoadedCallback(MainActivity.this);
      }

      @Override
      public void onError(String error) {
          Toast.makeText(MainActivity.this, "server error.", Toast.LENGTH_LONG).show();
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
    Toast.makeText(this, "server not respond, " +
      "please try again later.", Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onMapLoaded() {
    if (listPositions.size() > 0) {
      for (Position position : listPositions) {

        BusDetails busDetails = BusDetailsHelper.parseBusDetails(this , position);
        LatLng pos = busDetails.getLatLng();
        String name = busDetails.getName();
        String details = busDetails.getDetail();
        String driverName = busDetails.getDriverName();
        BitmapDescriptor icon = busDetails.getIcon();

        //mokhtasate noqte shoroe map ro moshakhas mikonad
        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //mishakhas kardane 1 noqte roye map
        MarkerOptions markerOptions = new MarkerOptions().title(name)
          .snippet(details).position(pos).icon(icon);
        map.addMarker(markerOptions);
      }
    }
  }


}
