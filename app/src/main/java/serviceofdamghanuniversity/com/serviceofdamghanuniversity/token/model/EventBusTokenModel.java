package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.model;

public class EventBusTokenModel {

  private boolean isTokenLoaded;
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isTokenLoaded() {
    return isTokenLoaded;
  }

  public void setTokenLoaded(boolean tokenLoaded) {
    isTokenLoaded = tokenLoaded;
  }
}
