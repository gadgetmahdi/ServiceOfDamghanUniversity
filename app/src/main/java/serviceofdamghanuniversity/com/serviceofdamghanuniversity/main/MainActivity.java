package serviceofdamghanuniversity.com.serviceofdamghanuniversity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.BusPositionFragment;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.SplashFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SplashFragment splashFragment = SplashFragment.newInstance();

    BusPositionFragment busPositionFragment = BusPositionFragment.newInstance();

    splashFragment.setSplashFinished(() -> getSupportFragmentManager().beginTransaction()
      .replace(R.id.container, busPositionFragment)
      .commit()
    );

    getSupportFragmentManager().beginTransaction().replace(R.id.container, splashFragment).commit();
  }
}
