package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    if(busDetails.isBusIsOnline()) {
      viewHolderWorkoutAdapter.txtBusSituations.setText("Online");
      viewHolderWorkoutAdapter.txtBusSituations.setTextColor(context.getResources().getColor(R.color.bus_is_online));
      viewHolderWorkoutAdapter.txtBusUpdateTime.setVisibility(View.GONE);
      viewHolderWorkoutAdapter.txtBusSpeed.setVisibility(View.VISIBLE);
      viewHolderWorkoutAdapter.txtBusSpeed.setText(busDetails.getSpeed()+ " km/h");
      viewHolderWorkoutAdapter.txtBusSpeed.setTextColor(getSpeedColor(busDetails.getSpeed()));
    }else {
      viewHolderWorkoutAdapter.txtBusSpeed.setVisibility(View.GONE);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      String timeString = busDetails.getBusLastUpdateTime();
      try {
        Date busTime = df.parse(timeString);
        Date currentTime  = new Date();
        Log.w("mehdiTest" , busTime + "    " + currentTime);
        long timeDiff = currentTime.getTime() - busTime.getTime();
        viewHolderWorkoutAdapter.txtBusUpdateTime.setVisibility(View.VISIBLE);
        viewHolderWorkoutAdapter.txtBusUpdateTime.setText(calculateDiff(timeDiff));
      } catch (ParseException e) {
        e.printStackTrace();
      }

      viewHolderWorkoutAdapter.txtBusSituations.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
      viewHolderWorkoutAdapter.txtBusSituations.setText("Offline");
    }

    Drawable drawable = ContextCompat.getDrawable(context, busDetails.getIconId());
    viewHolderWorkoutAdapter.imgBus.setImageDrawable(drawable);

    viewHolderWorkoutAdapter.linearLayoutBuses.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        busAdapterSetOnClickListener.onClickListener(v, busDetails.getBusId());
      }
    });

  }

  private String calculateDiff(long timeDiff){
    long h = timeDiff / 3600000;
    long m = (timeDiff % 3600000) / 60000;
    if(h != 0) {
      return "(" + h + " hour/s " + m + " minutes" + " ago)";
    }else {
      return "(" + m + " minutes" + " ago)";
    }
  }

  private int getSpeedColor(double speed){
    if(speed >= 50){
      return context.getResources().getColor(R.color.bus_speed_is_more_than_normal);
    }else {
      return context.getResources().getColor(R.color.bus_speed_is_normal);
    }
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  class ViewHolderBusAdapter extends RecyclerView.ViewHolder {

    private LinearLayout linearLayoutBuses;

    private TextView txtBusName;
    private TextView txtBusDetails;
    private TextView txtBusSituations;
    private TextView txtBusUpdateTime;
    private TextView txtBusSpeed;

    private ImageView imgBus;

    ViewHolderBusAdapter(View itemView) {
      super(itemView);
      linearLayoutBuses = itemView.findViewById(R.id.linearLayoutBuses);
      txtBusName = itemView.findViewById(R.id.txtBusName);
      txtBusDetails = itemView.findViewById(R.id.txtBusDetails);
      imgBus = itemView.findViewById(R.id.imgBus);
      txtBusSituations = itemView.findViewById(R.id.txtBusSituations);
      txtBusUpdateTime = itemView.findViewById(R.id.txtBusUpdateTime);
      txtBusSpeed = itemView.findViewById(R.id.txtBusSpeed);
    }
  }
}

