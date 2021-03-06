package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;

public class DialogNoInternet {
  private static  AlertDialog dialog;
  public static void showIsNotConnection(Context context , BroadcastReceiver broadcastReceiver) {
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(context);
    builder.setCancelable(false);
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.custom_dilaog, null);
    final TextView txtTitle = view.findViewById(R.id.txt_title);
    final ImageView imgDialogIcon = view.findViewById(R.id.img_dialog_icon);
    final TextView txtContent = view.findViewById(R.id.txt_content);
    final Button btnExit = view.findViewById(R.id.btn_exit);

    imgDialogIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_no_wifi));

    builder.setView(view);
    txtTitle.setText(context.getString(R.string.no_connection_title));
    txtContent.setText(context.getString(R.string.no_connection_message));
    btnExit.setText(context.getString(R.string.no_connection_exit_button));
    btnExit.setOnClickListener(view1 -> {
      dialog.dismiss();
      context.unregisterReceiver(broadcastReceiver);
      android.os.Process.killProcess(android.os.Process.myPid());
      System.exit(1);
    });
    dialog = builder.create();
    dialog.show();
  }

  public static void dismissDialog(){
    if(dialog != null) {
      if (dialog.isShowing()) {
        dialog.dismiss();
      }
    }
  }
}
