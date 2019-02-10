package serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash;


public class SplashContract {
  public interface SplashPresenter{
    void loadNeededData();
    void onDestroy();
  }

  public interface SplashView{
    void onDataLoaded();
  }
}
