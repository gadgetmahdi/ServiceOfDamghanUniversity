package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

/**
 * create with mahdi gadget & mehdi vj 11/2018
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ResponseListener.Session , GoogleMap.OnMapLoadedCallback {

  WebServiceCaller webServiceCaller;
  GoogleMap map;
  private LatLng latLng;

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

    TokenDb tokenDb = new TokenDb(this);
    if (tokenDb.checkIsShCreated()) {
      webServiceCaller.createSession(this);
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
          latLng = new LatLng(positions.get(0).getLatitude() , positions.get(0).getLongitude());
        }

        map.setOnMapLoadedCallback(MainActivity.this);
      }

      @Override
      public void onError(String error) {
        Log.w("mehdiTest", error);
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
    getBusPositions();
  }

  @Override
  public void onError(String error) {
    Toast.makeText(this, "server not respond, " +
      "please try again later.", Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onMapLoaded() {
    if(latLng != null) {
      //mokhtasate noqte shoroe map ro moshakhas mikonad
      CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
      map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

      //mishakhas kardane 1 noqte roye map
      MarkerOptions markerOptions = new MarkerOptions().title("metivj.title")
        .snippet("metivj.snippet").position(latLng).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(R.drawable.ic_bus_green)));
      map.addMarker(markerOptions);
    }
  }




  public Bitmap getBitmapFromVectorDrawable(int drawableId) {
    Drawable drawable = ContextCompat.getDrawable(this , drawableId);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      drawable = (DrawableCompat.wrap(drawable)).mutate();
    }

    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
      drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
  }

}
