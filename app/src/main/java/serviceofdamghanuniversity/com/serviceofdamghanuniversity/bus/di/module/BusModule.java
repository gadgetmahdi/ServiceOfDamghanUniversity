package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.BusContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.presenter.BusPresenterImpl;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo.BusNetService;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo.BusRepo;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.general.RetrofitProvider;

@Module
public class BusModule {

  private BusContract.BusView view;
  private Context context;

  public BusModule(BusContract.BusView view, Context context) {
    this.view = view;
    this.context = context;
  }

  @Provides
  @Singleton
  public BusContract.BusPresenter busPresenterProvider() {
    BusNetService api = RetrofitProvider.provideRetrofit(
      HttpUrl.parse("http://skill.du.ac.ir:8082"), context).create(BusNetService.class);
    BusRepo repo = new BusRepo(api);
    return new BusPresenterImpl(view, context, repo);
  }

}
