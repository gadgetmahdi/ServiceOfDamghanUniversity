package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token;

public class TokenContract {
  public interface TokenPresenter{
    void getToken();
  }

  public interface TokenView{
    void onTokenLoaded(String token , Throwable throwable);
  }
}
