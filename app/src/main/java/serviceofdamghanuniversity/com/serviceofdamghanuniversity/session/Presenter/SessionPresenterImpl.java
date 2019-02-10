package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.Presenter;

import android.util.Log;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.SessionContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.repo.SessionRepo;

public class SessionPresenterImpl implements SessionContract.SessionPresenter {

  private static final String TAG = "SessionPresenterImpl";
  private SessionContract.SessionView view;
  private SessionRepo repo;
  private String token;

  public SessionPresenterImpl(SessionContract.SessionView view, SessionRepo repo, String token) {
    this.view = view;
    this.repo = repo;
    this.token = token;
  }

  @Override
  public void createSession() {
    Log.d(TAG, "createSession: " + token);
    repo.createSession(token).subscribe(
      user -> view.onSessionCreated(null),
      throwable -> view.onSessionCreated(throwable));
  }
}
