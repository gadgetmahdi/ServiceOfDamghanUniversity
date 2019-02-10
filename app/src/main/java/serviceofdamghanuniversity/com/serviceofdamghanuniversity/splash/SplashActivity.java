package serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.main.MainActivity;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.presenter.SplashPresenterImpl;

public class SplashActivity extends AppCompatActivity  implements SplashContract.SplashView{


  private SplashPresenterImpl presenter;
  private TextView txtDeveloper;
  private TextView txtCreateBy;
  private ImageView imgLogo;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    initView();
    startAnimations();
    presenter = new SplashPresenterImpl(this ,this);
    presenter.loadNeededData();
  }


  private void initView() {
    txtDeveloper = findViewById(R.id.txt_developer);
    txtCreateBy = findViewById(R.id.txt_create_by);
    imgLogo = findViewById(R.id.logo);
    progressBar = findViewById(R.id.progress_bar);
  }

  private void startAnimations() {
    Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
    anim.reset();
    txtDeveloper.clearAnimation();
    txtDeveloper.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
    anim.reset();
    txtCreateBy.clearAnimation();
    txtCreateBy.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(this, R.anim.top_to_down);
    anim.reset();
    imgLogo.clearAnimation();
    imgLogo.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
    anim.reset();
    progressBar.clearAnimation();
    progressBar.startAnimation(anim);

  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.onDestroy();
  }

  @Override
  public void onDataLoaded() {
    startActivity(new Intent(this , MainActivity.class));
    finish();
  }
}
