package serviceofdamghanuniversity.com.serviceofdamghanuniversity.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog.DialogRestartApp;

public class SettingActivity extends AppCompatActivity {

  private RadioButton radioGoogleMap;
  private RadioButton radioOpenStreetMap;
  private Button btnSave;
  private String stringSelected;
  private static final String TAG = "SettingActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    Toolbar toolbar = findViewById(R.id.prod_toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationOnClickListener(v -> finish());

    initView();

    if (SettingHelper.getSettingData(this).equals(SettingHelper.GoogleMap)) {
      radioGoogleMap.setChecked(true);
      radioOpenStreetMap.setChecked(false);
    } else {
      radioGoogleMap.setChecked(false);
      radioOpenStreetMap.setChecked(true);
    }

    btnSave.setEnabled(false);

    radioGoogleMap.setOnCheckedChangeListener((compoundButton, b)
      -> {
      if(b) {
        stringSelected = SettingHelper.GoogleMap;
        btnSave.setEnabled(true);
      }else {
        stringSelected = SettingHelper.OpenStreetMap;
        btnSave.setEnabled(true);
      }
    });



    btnSave.setOnClickListener(view -> {
      Log.d(TAG, "onCreate: "  + stringSelected);
      SettingHelper.saveSettingData(SettingActivity.this, stringSelected);
      btnSave.setEnabled(false);
      DialogRestartApp.showRestartDialog(SettingActivity.this);
    });
  }

  private void initView() {

    btnSave = findViewById(R.id.btn_save);
    radioGoogleMap = findViewById(R.id.radio_google_map);
    radioOpenStreetMap = findViewById(R.id.radio_open_street_map);
  }
}
