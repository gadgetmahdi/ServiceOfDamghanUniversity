package serviceofdamghanuniversity.com.serviceofdamghanuniversity;


import android.animation.Animator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetailsHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.BitmapToVectorDrawable;


public class MapFragment extends Fragment implements OnMapReadyCallback {


  @BindView(R.id.floatingActionButton)
  FloatingActionButton floatingActionButton;

  @BindView(R.id.map_type_FAB)
  FloatingActionButton map_type_FAB;

  @BindView(R.id.map_type_selection)
  ConstraintLayout map_type_selection;

  @BindView(R.id.map_type_default_background)
  View map_type_default_background;

  @BindView(R.id.map_type_default)
  ImageView map_type_default;

  @BindView(R.id.map_type_satellite_background)
  View map_type_satellite_background;

  @BindView(R.id.map_type_satellite)
  ImageView map_type_satellite;

  @BindView(R.id.map_type_terrain_background)
  View map_type_terrain_background;

  @BindView(R.id.mapView)
  MapView mapView;

  @BindView(R.id.map_type_terrain)
  ImageView map_type_terrain;

  @BindView(R.id.map_type_default_text)
  TextView map_type_default_text;

  @BindView(R.id.map_type_satellite_text)
  TextView map_type_satellite_text;

  @BindView(R.id.map_type_terrain_text)
  TextView map_type_terrain_text;


  private GoogleMap map;
  private ArrayList<Position> mListPositions = new ArrayList<>();
  private Unbinder unbinder;
  private Location myLocation;
  private boolean isSetCameraToMyLocation = false;
  private boolean isMapLoaded = false;
  private int busIdSelected = -1;
  private Map<Integer, LatLng> hmBusIdAndPos = new HashMap<>();

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_map, container, false);

    unbinder = ButterKnife.bind(this, view);


    if (getActivity() != null) {
      ((MainActivityN) getActivity()).setOnPosition(new MainActivityN.PositionsForMap() {
        @Override
        public void onBusPositionsProvided(ArrayList<Position> listPositions) {
          mListPositions = listPositions;

          if (isMapLoaded) {
            getPosAndParse();
          }
        }

        @Override
        public void onMyPositionsProvided(Location location) {
          myLocation = location;
          floatingActionButton.show();
        }

        @Override
        public void onBusSelected(int busId) {
          busIdSelected = busId;
          isSetCameraToMyLocation = false;
          if (isMapLoaded) {
            setMapCamera(hmBusIdAndPos.get(busId));
          }
        }
      });
    }


    mapView.onCreate(savedInstanceState);

    mapView.onResume(); // needed to get the map to display immediately

    mapView.getMapAsync(this);

    try {
      MapsInitializer.initialize(getActivity().getApplicationContext());
    } catch (Exception e) {
      e.printStackTrace();
    }


    return view;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();

    if (getActivity() != null) {
      ((MainActivityN) getActivity()).setOnPosition(null);
    }

    unbinder.unbind();
  }

  @OnClick(R.id.floatingActionButton)
  public void floatingActionButton() {
    if (myLocation != null) {
      LatLng pos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

      CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
      map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

      isSetCameraToMyLocation = true;
    }
  }


  @OnClick(R.id.map_type_FAB)
  public void map_type_FAB() {
    // Conduct the animation if the FAB is invisible (window open)
    if (map_type_selection.getVisibility() == View.INVISIBLE) {

      // Start animator close and finish at the FAB position
      Animator animator = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        animator = ViewAnimationUtils.createCircularReveal(map_type_selection,
          map_type_selection.getWidth() - (map_type_FAB.getWidth() / 2),
          map_type_FAB.getHeight() / 2,
          ((float) map_type_selection.getWidth()),
          map_type_FAB.getWidth() / 2f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animator) {

          }

          @Override
          public void onAnimationEnd(Animator animator) {
            map_type_selection.setVisibility(View.VISIBLE);
          }

          @Override
          public void onAnimationCancel(Animator animator) {

          }

          @Override
          public void onAnimationRepeat(Animator animator) {

          }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            map_type_selection.setVisibility(View.VISIBLE);
          }
        }, 100);
        animator.start();
        map_type_FAB.show();


      }
    }


  }

  @OnClick(R.id.map_type_default)
  public void map_type_default() {
    map_type_default_background.setVisibility(View.VISIBLE);
    map_type_satellite_background.setVisibility(View.INVISIBLE);
    map_type_terrain_background.setVisibility(View.INVISIBLE);
    map_type_default_text.setTextColor(Color.BLUE);
    map_type_satellite_text.setTextColor(Color.parseColor("#808080"));
    map_type_terrain_text.setTextColor(Color.parseColor("#808080"));
    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
  }

  @OnClick(R.id.map_type_satellite)
  public void map_type_satellite() {
    map_type_default_background.setVisibility(View.INVISIBLE);
    map_type_satellite_background.setVisibility(View.VISIBLE);
    map_type_terrain_background.setVisibility(View.INVISIBLE);
    map_type_default_text.setTextColor(Color.parseColor("#808080"));
    map_type_satellite_text.setTextColor(Color.BLUE);
    map_type_terrain_text.setTextColor(Color.parseColor("#808080"));
    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
  }

  @OnClick(R.id.map_type_terrain)
  public void map_type_terrain() {
    map_type_default_background.setVisibility(View.INVISIBLE);
    map_type_satellite_background.setVisibility(View.INVISIBLE);
    map_type_terrain_background.setVisibility(View.VISIBLE);
    map_type_default_text.setTextColor(Color.parseColor("#808080"));
    map_type_satellite_text.setTextColor(Color.parseColor("#808080"));
    map_type_terrain_text.setTextColor(Color.BLUE);
    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    isMapLoaded = true;
    map = googleMap;
    setSettingForMap(map);

    LatLng selfPos = new LatLng(36.168917,54.323100);
    setMapCamera(selfPos);

    if (getActivity() != null) {
      if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
      }
    }

    // When map is initially loaded, determine which map type option to 'select'

    switch (map.getMapType()) {
      case GoogleMap.MAP_TYPE_SATELLITE:
        map_type_satellite_background.setVisibility(View.VISIBLE);
        map_type_satellite_text.setTextColor(Color.BLUE);
        break;

      case GoogleMap.MAP_TYPE_TERRAIN:
        map_type_terrain_background.setVisibility(View.VISIBLE);
        map_type_terrain_text.setTextColor(Color.BLUE);
        break;

      default:
        map_type_default_background.setVisibility(View.VISIBLE);
        map_type_default_text.setTextColor(Color.BLUE);
        break;
    }


    map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
    {
      @Override
      public void onMapClick(LatLng arg0)
      {
        if (map_type_selection.getVisibility() == View.VISIBLE) {
          map_type_selection.setVisibility(View.INVISIBLE);
        }
      }
    });


  }

  private void setSettingForMap(GoogleMap map) {
    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    map.getUiSettings().setCompassEnabled(true);
    map.getUiSettings().setMapToolbarEnabled(true);
    map.getUiSettings().setZoomGesturesEnabled(true);
    map.getUiSettings().setScrollGesturesEnabled(true);
    map.getUiSettings().setTiltGesturesEnabled(true);
    map.getUiSettings().setRotateGesturesEnabled(true);

    if (getActivity() != null) {
      map.setMapStyle(
        MapStyleOptions.loadRawResourceStyle(
          getActivity(), R.raw.theme_night));
    }
  }

  private void getPosAndParse() {
    if (mListPositions.size() > 0) {

      hmBusIdAndPos.clear();

      for (Position position : mListPositions) {
        BusDetails busDetails = BusDetailsHelper.parseBusDetails(getActivity(), position);
        LatLng pos = busDetails.getLatLng();
        String name = busDetails.getName();
        int busId = busDetails.getBusId();
        String details = busDetails.getDetail();
        String driverName = busDetails.getDriverName();
        int iconId = busDetails.getIconId();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
          BitmapToVectorDrawable.getVectorDrawable(getActivity(), iconId));

        hmBusIdAndPos.put(busId, pos);

        /*if (!isSetCameraToMyLocation) {
          if (busId == busIdSelected) {
            setMapCamera(pos);
          } else if (busIdSelected == -1) {
            //setMapCamera(pos);
          }
        }*/

        MarkerOptions markerOptions = new MarkerOptions().title(name)
          .snippet(details).position(pos).icon(icon);
        map.addMarker(markerOptions);
      }
    }
  }


  private void setMapCamera(LatLng pos) {
    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(18).build();
    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }
}
