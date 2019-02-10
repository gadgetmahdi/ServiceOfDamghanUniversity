package serviceofdamghanuniversity.com.serviceofdamghanuniversity.buslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.model.EventBusLocationModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetailsHelper;


public class BusFragment extends Fragment {


  @BindView(R.id.recyclerViewBuses)
  RecyclerView recyclerViewBuses;

  @BindView(R.id.txtNoBuses)
  TextView txtNoBuses;

  private static final String TAG = "BusFragment";
  private BusAdapter busAdapter;
  private Unbinder unbinder;
  private ArrayList<BusDetails> busDetailsList = new ArrayList<>();


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_bus, container, false);

    unbinder = ButterKnife.bind(this, view);

    EventBus.getDefault().register(this);


    LinearLayoutManager layoutManager
      = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    recyclerViewBuses.setLayoutManager(layoutManager);

    busAdapter = new BusAdapter(getContext(), busDetailsList, (busId)
      -> {
      EventBusSelectedModel eventBusSelectedModel = new EventBusSelectedModel();
      eventBusSelectedModel.setSelectedId((long) busId);
      EventBus.getDefault().post(eventBusSelectedModel);
    });

    recyclerViewBuses.setLayoutManager(layoutManager);
    recyclerViewBuses.setAdapter(busAdapter);


    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBusData(EventBusLocationModel eventBusLocationModel) {
    parseBusesList(eventBusLocationModel.getListPositions());
  }

  private void parseBusesList(List<Position> listPositions) {
    busDetailsList.clear();

    for (Position position : listPositions) {
      try {
        recyclerViewBuses.setVisibility(View.VISIBLE);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (getActivity() != null) {
        BusDetails busDetails = BusDetailsHelper.parseBusDetails(getActivity(), position);
        busDetailsList.add(busDetails);

        busAdapter.notifyDataSetChanged();

      }


    }

    if (listPositions.size() == 0 && txtNoBuses.getVisibility() == View.GONE) {
      txtNoBuses.setVisibility(View.VISIBLE);
      recyclerViewBuses.setVisibility(View.GONE);
    }

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    EventBus.getDefault().unregister(this);
  }

}
