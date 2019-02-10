package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.RetrofitProvider;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.TokenContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.presenter.TokenPresenterImpl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.repo.TokenNetService;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.repo.TokenRepo;

import static serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.ClientProvider.provideOkHttpClientWithInterceptor;

@Module
public class TokenModule {

  private TokenContract.TokenView view;
  private Context context;

  public TokenModule(TokenContract.TokenView view, Context context) {
    this.view = view;
    this.context = context;
  }

  @Singleton
  @Provides
  public TokenContract.TokenPresenter tokenPresenterProvider() {
    OkHttpClient okHttpClient = provideOkHttpClientWithInterceptor();
    TokenNetService api = RetrofitProvider.provideRetrofitWithInterceptor(
      HttpUrl.parse("http://bit.do/"), okHttpClient).create(TokenNetService.class);
    TokenRepo repo = new TokenRepo(api);
    return new TokenPresenterImpl(repo, view , context , okHttpClient);
  }
}
