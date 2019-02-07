package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.RetrofitProvider;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.BusPositionContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.presenter.BusPositionPresenterImpl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo.BusPositionNetService;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo.BusPositionRepo;

@Module
public class BusPositionModule {

  private BusPositionContract.BusPositionView view;
  private Context context;

  public BusPositionModule(BusPositionContract.BusPositionView view, Context context) {
    this.view = view;
    this.context = context;
  }

  @Singleton
  @Provides
  public BusPositionContract.BusPositionPresenter busPositionPresenterProvider() {
    BusPositionNetService api = RetrofitProvider.provideRetrofit(
      HttpUrl.parse(""), context).create(BusPositionNetService.class);
    BusPositionRepo repo = new BusPositionRepo(api);
    return new BusPositionPresenterImpl(view, repo);
  }
}
