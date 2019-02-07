package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;

public class DialogNoInternet {
  public static void showIsNotConnection(Context context) {
    AlertDialog dialog;
    AlertDialog.Builder builder;
    builder = new AlertDialog.Builder(context);
    builder.setCancelable(false);
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.custom_dilaog, null);
    final TextView txtTitle = view.findViewById(R.id.txt_title);
    final TextView txtContent = view.findViewById(R.id.txt_content);
    final Button btnExit = view.findViewById(R.id.btn_exit);


    builder.setView(view);
    txtTitle.setText(context.getString(R.string.no_connection_title));
    txtContent.setText(context.getString(R.string.no_connection_message));
    btnExit.setText(context.getString(R.string.no_connection_exit_button));
    btnExit.setOnClickListener(view1 -> {
      Intent homeIntent = new Intent(Intent.ACTION_MAIN);
      homeIntent.addCategory(Intent.CATEGORY_HOME);
      homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      context.startActivity(homeIntent);
    });
    dialog = builder.create();
    dialog.show();
  }
}
