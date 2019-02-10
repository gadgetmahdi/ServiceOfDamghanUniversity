package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class PermissionClass extends AppCompatActivity {

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Intent intent = new Intent("PERMISSION_RECEIVER");
    intent.putExtra("requestCode", requestCode);
    intent.putExtra("permissions", permissions);
    intent.putExtra("grantResults", grantResults);
    sendBroadcast(intent);
  }
}
