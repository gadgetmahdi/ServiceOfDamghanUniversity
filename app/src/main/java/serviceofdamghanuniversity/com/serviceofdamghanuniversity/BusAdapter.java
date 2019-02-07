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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.modelStub.BusDetails;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolderBusAdapter> {

  public interface BusAdapterSetOnClickListener {
    void onClickListener(View view, int busId);
  }

  private ArrayList<BusDetails> list;
  private BusAdapterSetOnClickListener busAdapterSetOnClickListener;
  private Context context;

  BusAdapter(Context context, ArrayList<BusDetails> list, BusAdapterSetOnClickListener busAdapterSetOnClickListener) {
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

    if(!busDetails.getDetail().equals("")) {
      viewHolderWorkoutAdapter.txtBusDetails.setText(busDetails.getDetail());
    }

    String busTime = busDetails.getBusLastUpdateTime();

   /* switch (busDetails.getBusStatus()) {
      case "online":
        setValueForOnlineBuses(context, viewHolderWorkoutAdapter, busDetails);
        break;
      case "offline":
        setValueForOfflineBuses(context, viewHolderWorkoutAdapter, busTime);
        break;
      default:
        setValueForUnknownBuses(context, viewHolderWorkoutAdapter);
        break;
    }*/

   if(isDeviceIsOnline(getDiffTime(busTime))){
     setValueForOnlineBuses(context, viewHolderWorkoutAdapter, busDetails);
   }else {
     setValueForOfflineBuses(context, viewHolderWorkoutAdapter, busTime);
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

  private static void setValueForOnlineBuses(Context context, ViewHolderBusAdapter viewHolderWorkoutAdapter, BusDetails busDetails) {
    viewHolderWorkoutAdapter.txtBusSituations.setText(context.getString(R.string.bus_is_online));
    viewHolderWorkoutAdapter.txtBusUpdateTime.setVisibility(View.GONE);
    viewHolderWorkoutAdapter.txtBusSpeed.setVisibility(View.VISIBLE);
    viewHolderWorkoutAdapter.txtBusSpeed.setText(context.getString(R.string.bus_speed, busDetails.getSpeed()));
    viewHolderWorkoutAdapter.txtBusSpeed.setTextColor(getSpeedColor(context, busDetails.getSpeed()));

    viewHolderWorkoutAdapter.txtBusSituations.setTextColor(context.getResources().getColor(R.color.bus_is_online));
    viewHolderWorkoutAdapter.txtBusName.setTextColor(context.getResources().getColor(R.color.bus_is_online));
    viewHolderWorkoutAdapter.txtBusDetails.setTextColor(context.getResources().getColor(R.color.bus_is_online));

  }

  private static void setValueForOfflineBuses(Context context, ViewHolderBusAdapter viewHolderWorkoutAdapter, String busTime) {
    viewHolderWorkoutAdapter.txtBusSpeed.setVisibility(View.GONE);

    viewHolderWorkoutAdapter.txtBusUpdateTime.setVisibility(View.VISIBLE);
    viewHolderWorkoutAdapter.txtBusUpdateTime.setText(calculateDiff(getDiffTime(busTime)));

    viewHolderWorkoutAdapter.txtBusSituations.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusName.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusDetails.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusSituations.setText(context.getString(R.string.bus_is_offline));

  }

  private static void setValueForUnknownBuses(Context context, ViewHolderBusAdapter viewHolderWorkoutAdapter) {
    viewHolderWorkoutAdapter.txtBusSpeed.setVisibility(View.GONE);
    viewHolderWorkoutAdapter.txtBusUpdateTime.setVisibility(View.GONE);

    viewHolderWorkoutAdapter.txtBusSituations.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusName.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusDetails.setTextColor(context.getResources().getColor(R.color.bus_is_offline));
    viewHolderWorkoutAdapter.txtBusSituations.setText(context.getString(R.string.bus_is_offline));

  }

  private static long getDiffTime(String timeString) {
    Locale locale = new Locale("fa_ ");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale);
    //
    try {
      Date busTime = df.parse(timeString);
      Date currentTime = new Date();
      return currentTime.getTime() - busTime.getTime();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }


  private boolean isDeviceIsOnline(long diff) {
    return !(diff >= 30000);
  }

  private static String calculateDiff(long timeDiff) {
    long h = timeDiff / 3600000;
    long m = (timeDiff % 3600000) / 60000;
    if (h != 0) {
      return "(" + h + " ساعت "  + "و "+ m + " دقیقه" + " پیش)";
    } else {
      return "(" + m + " دقیقه" + " پیش)";
    }
  }

  private static int getSpeedColor(Context context, double speed) {
    if (speed >= 50) {
      return context.getResources().getColor(R.color.bus_speed_is_more_than_normal);
    } else {
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

