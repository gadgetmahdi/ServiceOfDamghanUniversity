package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.RetrofitProvider;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.Presenter.SessionPresenterImpl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.SessionContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.repo.SessionNetService;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.repo.SessionRepo;

@Module
public class SessionModule {

  private SessionContract.SessionView view;
  private Context context;
  private String token;

  public SessionModule(SessionContract.SessionView view, Context context , String token) {
    this.view = view;
    this.context = context;
    this.token = token;
  }

  @Singleton
  @Provides
  public SessionContract.SessionPresenter sessionPresenterProvider() {
    SessionNetService api = RetrofitProvider.provideRetrofit(
      HttpUrl.parse("http://skill.du.ac.ir:8082"), context).create(SessionNetService.class);
    SessionRepo repo = new SessionRepo(api);
    return new SessionPresenterImpl(view, repo , token);
  }
}
