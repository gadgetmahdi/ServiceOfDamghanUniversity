package serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.action.SplashCallback;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.presenter.SplashPresenterImpl;

public class SplashFragment extends Fragment implements SplashContract.SplashView{

  private static final String TAG = "SplashFragment";
  private SplashPresenterImpl presenter;
  private SplashCallback splashFinished;
  private TextView txtDeveloper;
  private TextView txtCreateBy;
  private ImageView imgLogo;
  private ProgressBar progressBar;


  public SplashFragment() {
    // Required empty public constructor
  }

  public static SplashFragment newInstance() {
    SplashFragment fragment = new SplashFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter = new SplashPresenterImpl(getContext() ,this);
    presenter.loadNeededData();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_splash, container, false);
    initView(view);
    startAnimations();
    return view;
  }

  private void initView(View view) {
    txtDeveloper = view.findViewById(R.id.txt_developer);
    txtCreateBy = view.findViewById(R.id.txt_create_by);
    imgLogo = view.findViewById(R.id.logo);
    progressBar = view.findViewById(R.id.progress_bar);
  }


  private void startAnimations() {
    Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
    anim.reset();
    txtDeveloper.clearAnimation();
    txtDeveloper.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
    anim.reset();
    txtCreateBy.clearAnimation();
    txtCreateBy.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_down);
    anim.reset();
    imgLogo.clearAnimation();
    imgLogo.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
    anim.reset();
    progressBar.clearAnimation();
    progressBar.startAnimation(anim);

  }


  public void setSplashFinished(SplashCallback splashFinished) {
    this.splashFinished = splashFinished;
  }


  @Override
  public void onStop() {
    super.onStop();
    presenter.onDestroy();
  }


  @Override
  public void onDataLoaded() {
    Log.d(TAG, "onDataLoaded: ");
    splashFinished.call();
  }
}
