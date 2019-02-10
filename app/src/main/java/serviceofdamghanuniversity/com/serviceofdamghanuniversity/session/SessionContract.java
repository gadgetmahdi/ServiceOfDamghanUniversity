package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session;

public class SessionContract {
  public interface SessionPresenter{
    void createSession();
  }

  public interface SessionView{
    void onSessionCreated(Throwable throwable);
  }
}
