package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;

public class BusPositionFragment extends Fragment  implements BusPositionContract.BusPositionView {

  public static BusPositionFragment newInstance() {
    return new BusPositionFragment();
  }

  @Inject
  BusPositionContract.BusPositionPresenter presenter;


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.activity_position, container, false);

   /* BusPositionModule busPositionModule = new BusPositionModule(this , getContext());
    DaggerBusPositionComponent
      .builder()
      .busPositionModule(busPositionModule)
      .build()
      .inject(this);
*/
    presenter.getBusLocation();
    return view;
  }


  @Override
  public void onBusLocationReceived() {

  }
}
