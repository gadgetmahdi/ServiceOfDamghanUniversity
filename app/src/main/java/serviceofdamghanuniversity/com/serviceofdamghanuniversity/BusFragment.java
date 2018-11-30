package serviceofdamghanuniversity.com.serviceofdamghanuniversity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetailsHelper;


public class BusFragment extends Fragment {

  public interface BusIdCallBack {
    void onBusSelected(int busId);
  }

  public void setBusIdCallBack(BusIdCallBack busIdCallBack){
    this.mBusIdCallBack = busIdCallBack;
  }

  @BindView(R.id.recyclerViewBuses)
  RecyclerView recyclerViewBuses;

  @BindView(R.id.txtNoBuses)
  TextView txtNoBuses;

  private BusAdapter busAdapter;
  private Unbinder unbinder;
  private BusIdCallBack mBusIdCallBack;
  private ArrayList<BusDetails> busDetailsList = new ArrayList<>();


  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_bus, container, false);

    unbinder = ButterKnife.bind(this, view);

    if (getActivity() != null) {
      ((MainActivityN) getActivity()).setOnPositionsForBuses(new MainActivityN.PositionsForBuses() {

        @Override
        public void onBusPositionsProvided(ArrayList<Position> listPositions) {
          parseBusesList(listPositions);
        }
      });
    }

    LinearLayoutManager layoutManager
      = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    recyclerViewBuses.setLayoutManager(layoutManager);

     busAdapter = new BusAdapter(getContext(), busDetailsList, new BusAdapter.BusAdapterSetOnClickListener() {
      @Override
      public void onClickListener(View view, int busId) {
        if(mBusIdCallBack != null){
          mBusIdCallBack.onBusSelected(busId);
        }

      }

    });

    recyclerViewBuses.setLayoutManager(layoutManager);
    recyclerViewBuses.setAdapter(busAdapter);


    return view;
  }


  private void parseBusesList(ArrayList<Position> listPositions) {
    busDetailsList.clear();

    for (Position position : listPositions) {
      try {
        recyclerViewBuses.setVisibility(View.VISIBLE);
      }catch (Exception e){
        e.printStackTrace();
      }
      if(getActivity() != null) {
        BusDetails busDetails = BusDetailsHelper.parseBusDetails(getActivity(), position);
        busDetailsList.add(busDetails);

        busAdapter.notifyDataSetChanged();

      }


    }

    if(listPositions.size() == 0 && txtNoBuses.getVisibility() == View.GONE){
      txtNoBuses.setVisibility(View.VISIBLE);
      recyclerViewBuses.setVisibility(View.GONE);
    }

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

}
