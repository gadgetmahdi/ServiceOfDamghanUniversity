package serviceofdamghanuniversity.com.serviceofdamghanuniversity.main;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.buslist.BusFragment;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.buslist.EventBusSelectedModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.EventBusInternetModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.dialog.DialogNoInternet;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.utile.NetworkChangeReceiver;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.GoogleMapFragment;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.map.OpenStreetMapFragment;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.BusPositionClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.setting.SettingActivity;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.setting.SettingHelper;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  @BindView(R.id.navigation)
  NavigationView navigation;

  private static final String TAG = "MainActivity";
  private ActionBarDrawerToggle toggle;
  private BroadcastReceiver networkChangeReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);

    toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    setSupportActionBar(toolbar);


    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      // getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_24dp);
    }

    navigation.setNavigationItemSelectedListener(item -> {
      int id = item.getItemId();
      switch (id) {
        case R.id.nav_map:
          viewPager.setCurrentItem(0);
          drawerLayout.closeDrawers();
          return true;
        case R.id.nav_buses:
          viewPager.setCurrentItem(1);
          drawerLayout.closeDrawers();
          return true;
        case R.id.nav_report:
          sendIssue();
          drawerLayout.closeDrawers();
          return true;
        case R.id.nav_setting:
          startActivity(new Intent(this, SettingActivity.class));
          drawerLayout.closeDrawers();
          return true;

        default:
          return true;
      }

    });

    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);

    IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(networkChangeReceiver = new NetworkChangeReceiver(), intentFilter);

    new BusPositionClass(this);
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(networkChangeReceiver);
    EventBus.getDefault().unregister(this);
  }


  private void sendIssue() {
    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    emailIntent.setType("plain/text");

    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"damghanservice@gmail.com"});
    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.report_a_problem));
    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.txt_late_service);
    emailIntent.setPackage("com.google.android.gm");
    if (emailIntent.resolveActivity(getPackageManager()) != null)
      startActivity(Intent.createChooser(emailIntent, getString(R.string.report_a_problem)));
    else
      Toast.makeText(this, "اپ Gmail بر روی گوشی شما نصب نیست.", Toast.LENGTH_SHORT).show();
  }


  private void setupViewPager(ViewPager viewPager) {
    MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
    if (SettingHelper.getSettingData(this).equals(SettingHelper.GoogleMap)) {
      adapter.addFragment(new GoogleMapFragment(), getString(R.string.map));
    } else {
      adapter.addFragment(new OpenStreetMapFragment(), getString(R.string.map));
    }
    adapter.addFragment(new BusFragment(), getString(R.string.buses));
    viewPager.setAdapter(adapter);
  }


  @Subscribe(threadMode = ThreadMode.MAIN)
  public void internetStatus(EventBusInternetModel eventBusInternetModel) {
    if (eventBusInternetModel.isConnected()) {
      DialogNoInternet.dismissDialog();

    } else {
      DialogNoInternet.showIsNotConnection(this, networkChangeReceiver);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBusSelected(EventBusSelectedModel eventBusSelectedModel) {
    viewPager.setCurrentItem(0);
  }
}
