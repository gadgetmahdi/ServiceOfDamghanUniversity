package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolderBusAdapter> {

  public interface BusAdapterSetOnClickListener {
    void onClickListener(View view, int busId);
  }

  private ArrayList<BusDetails> list;
  private BusAdapterSetOnClickListener busAdapterSetOnClickListener;
  private Context context;

  public BusAdapter(Context context ,ArrayList<BusDetails> list, BusAdapterSetOnClickListener busAdapterSetOnClickListener) {
    this.context = context;
    this.list = list;
    this.busAdapterSetOnClickListener = busAdapterSetOnClickListener;
  }



  @NonNull
  @Override
  public BusAdapter.ViewHolderBusAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemViewWorkout = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.bus_datils_row, parent, false);


    return new ViewHolderBusAdapter(itemViewWorkout);
  }

  @Override
  public void onBindViewHolder(@NonNull final BusAdapter.ViewHolderBusAdapter holder, final int position) {
    BusDetails busDetails = list.get(position);

    setValue(holder, busDetails);

  }


  private void setValue(ViewHolderBusAdapter viewHolderWorkoutAdapter, final BusDetails busDetails) {
    viewHolderWorkoutAdapter.txtBusName.setText(busDetails.getName());
    viewHolderWorkoutAdapter.txtBusDetails.setText(busDetails.getDetail());

    Drawable drawable = ContextCompat.getDrawable(context, busDetails.getIconId());
    viewHolderWorkoutAdapter.imgBus.setImageDrawable(drawable);

    viewHolderWorkoutAdapter.linearLayoutBuses.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        busAdapterSetOnClickListener.onClickListener(v, busDetails.getBusId());
      }
    });

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  class ViewHolderBusAdapter extends RecyclerView.ViewHolder {

    private LinearLayout linearLayoutBuses;

    private TextView txtBusName;
    private TextView txtBusDetails;

    private ImageView imgBus;

    ViewHolderBusAdapter(View itemView) {
      super(itemView);
      linearLayoutBuses = itemView.findViewById(R.id.linearLayoutBuses);
      txtBusName = itemView.findViewById(R.id.txtBusName);
      txtBusDetails = itemView.findViewById(R.id.txtBusDetails);
      imgBus = itemView.findViewById(R.id.imgBus);
    }
  }
}

