package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.repo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.User;

public class SessionRepo {

  private SessionNetService api;

  public SessionRepo(SessionNetService api) {
    this.api = api;
  }

  public Observable<User> createSession(String token) {
    return api.createSession(token)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }
}
