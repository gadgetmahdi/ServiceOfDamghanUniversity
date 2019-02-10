package serviceofdamghanuniversity.com.serviceofdamghanuniversity.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.buslist.EventBusSelectedModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.model.EventBusLocationModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetailsHelper;

import static serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.utile.BusHelper.getDiffTime;
import static serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.utile.BusHelper.isDeviceIsOnline;
import static serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.BitmapToVectorDrawable.resize;

public class OpenStreetMapFragment extends Fragment {

  private static final String TAG = "OpenStreetMapFragment";
  private Context context;
  private MapView map = null;
  private LongSparseArray<LatLng> mListBusIdAndPos = new LongSparseArray<>();
  private ArrayList<OverlayItem> items = new ArrayList<>();


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_open_street, container, false);

    Context ctx = getContext();
    Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
    initView(view);
    //map.setTileSource(TileSourceFactory.MAPNIK);
    showCustomLocationOnMap();

    EventBus.getDefault().register(this);

    return view;
  }

  private void showCustomLocationOnMap() {
    IMapController mapController = map.getController();
    mapController.setZoom(19.0);
    GeoPoint startPoint = new GeoPoint(36.168917, 54.323100);
    mapController.setCenter(startPoint);
  }

  private void setMapCamera(LatLng pos) {
    if (pos != null) {
      IMapController mapController = map.getController();
      mapController.setZoom(19.0);
      GeoPoint startPoint = new GeoPoint(pos.latitude, pos.longitude);
      mapController.setCenter(startPoint);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onLocationDataLoaded(EventBusLocationModel eventBusLocationModel) {
    getPosAndParse(eventBusLocationModel.getListPositions());
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBusSelected(EventBusSelectedModel eventBusSelectedModel) {
    setMapCamera(mListBusIdAndPos.get(eventBusSelectedModel.getSelectedId()));
  }

  private void getPosAndParse(List<Position> listPositions) {
    if (listPositions.size() > 0) {

      mListBusIdAndPos.clear();
      items.clear();
      map.getOverlays().clear();

      for (Position position : listPositions) {
        if (getActivity() != null) {
          BusDetails busDetails = BusDetailsHelper.parseBusDetails(getActivity(), position);
          LatLng pos = busDetails.getLatLng();
          String name = busDetails.getName();
          int busId = busDetails.getBusId();

          String details = null;
          if (busDetails.getDetail() != null) {
            details = busDetails.getDetail();
          }
          //String driverName = busDetails.getDriverName();
          String busTime = busDetails.getBusLastUpdateTime();

          int iconId;
          if (isDeviceIsOnline(getDiffTime(busTime))) {
            iconId = busDetails.getIconId();
          } else {
            iconId = R.drawable.bus_offline;

          }

          mListBusIdAndPos.append(busId, pos);


          Drawable newMarker = resize(this.getResources().getDrawable(iconId), context);

          OverlayItem overlayItem = new OverlayItem(name, details, new GeoPoint(pos.latitude, pos.longitude));
          overlayItem.setMarker(newMarker);
          items.add(overlayItem);

        }

      }
    }

    ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<>(context, items,
      new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
        @Override
        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
          //do something
          return true;
        }

        @Override
        public boolean onItemLongPress(final int index, final OverlayItem item) {
          return true;
        }
      });
    mOverlay.setFocusItemsOnTap(true);

    map.getOverlays().add(mOverlay);

  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
  }

  private void initView(View view) {
    map = view.findViewById(R.id.map);
  }


  public void onResume() {
    super.onResume();
    map.onResume();
  }

  public void onPause() {
    super.onPause();
    map.onPause();
  }
}
