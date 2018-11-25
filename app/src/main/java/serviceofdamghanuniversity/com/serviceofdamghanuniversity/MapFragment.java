package serviceofdamghanuniversity.com.serviceofdamghanuniversity;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

  private GoogleMap map;
  private ArrayList<Position> mListPositions = new ArrayList<>();
  private Unbinder unbinder;
  private Location myLocation;
  private boolean isSetCameraToMyLocation = false;
  private boolean isMapLoaded = false;
  private int busIdSelected = -1;
  private Map<Integer, LatLng> hmBusIdAndPos = new HashMap<Integer, LatLng>();

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_map, container, false);


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


    unbinder = ButterKnife.bind(this, view);


    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
      .findFragmentById(R.id.map_fragment);
    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
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
  public void onClick() {
    if (myLocation != null) {
      LatLng pos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

      CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
      map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

      isSetCameraToMyLocation = true;
    } else {
      Toast.makeText(getActivity(), "your location not available.", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    isMapLoaded = true;
    map = googleMap;
    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    if(getActivity() != null) {
      if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
      }
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

        if (!isSetCameraToMyLocation) {
          if (busId == busIdSelected) {
            setMapCamera(pos);
          } else if (busIdSelected == -1) {
            //setMapCamera(pos);
          }
        }

        MarkerOptions markerOptions = new MarkerOptions().title(name)
          .snippet(details).position(pos).icon(icon);
        map.addMarker(markerOptions);
      }
    }
  }


  private void setMapCamera(LatLng pos) {
    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  }
}
