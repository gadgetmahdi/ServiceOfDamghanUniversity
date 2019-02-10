package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.component.DaggerTokenComponent;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.module.TokenModule;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.model.EventBusTokenModel;

public class TokenClass implements TokenContract.TokenView{

  @Inject
  TokenContract.TokenPresenter presenter;

  private Context context;

  public TokenClass(Context context) {
    this.context = context;

    getToken();
  }

  private void getToken(){
    TokenModule tokenModule = new TokenModule(this , context);
    DaggerTokenComponent.builder()
      .tokenModule(tokenModule)
      .build()
      .inject(this);

    presenter.getToken();

  }

  @Override
  public void onTokenLoaded(String token, Throwable throwable) {
    if(throwable == null){
      EventBusTokenModel tokenModel = new EventBusTokenModel();
      tokenModel.setTokenLoaded(true);
      tokenModel.setToken(token);
      EventBus.getDefault().post(tokenModel);
    }else {
      EventBusTokenModel tokenModel = new EventBusTokenModel();
      tokenModel.setTokenLoaded(false);
      tokenModel.setToken(null);
      EventBus.getDefault().post(tokenModel);
    }
  }
}
