package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;

public class DialogRestartApp {

  private static AlertDialog dialog;
  
  public static void showRestartDialog(Context context) {
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(context);
    builder.setCancelable(true);
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.custom_dilaog, null);
    final TextView txtTitle = view.findViewById(R.id.txt_title);
    final ImageView imgDialogIcon = view.findViewById(R.id.img_dialog_icon);
    final TextView txtContent = view.findViewById(R.id.txt_content);
    final Button btnExit = view.findViewById(R.id.btn_exit);

    imgDialogIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_restart_24dp));


    builder.setView(view);
    txtTitle.setText(context.getString(R.string.restart_app_title));
    txtContent.setText(context.getString(R.string.restart_app_message));
    btnExit.setText(context.getString(R.string.restart_app_message_button));
    btnExit.setOnClickListener(view1 -> {
      dialog.dismiss();
      triggerRebirth(context);
    });
    dialog = builder.create();
    dialog.show();
  }


  private static void triggerRebirth(Context context) {
    PackageManager packageManager = context.getPackageManager();
    Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
    ComponentName componentName = intent.getComponent();
    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
    context.startActivity(mainIntent);
    Runtime.getRuntime().exit(0);
  }
}
