package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.di.component.DaggerSessionComponent;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.di.module.SessionModule;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.model.EventBusSessionModel;

public class SessionClass implements SessionContract.SessionView {

  private static final String TAG = "SessionClass";

  @Inject
  SessionContract.SessionPresenter presenter;

  private Context context;
  private String token;

  public SessionClass(Context context , String token) {
    this.context = context;
    this.token = token;

    createSession();
  }

  private void createSession(){
    SessionModule sessionModule = new SessionModule(this , context , token);
    DaggerSessionComponent.builder()
      .sessionModule(sessionModule)
      .build()
      .inejct(this);

    presenter.createSession();
  }


  @Override
  public void onSessionCreated(Throwable throwable) {
    if(throwable == null){
      Log.d(TAG, "onSessionCreated: Created");
      EventBusSessionModel sessionModel = new EventBusSessionModel();
      sessionModel.setSessionCreated(true);
      EventBus.getDefault().post(sessionModel);
    }else {
      Log.d(TAG, "onSessionCreated: Error" + throwable.getMessage());
      EventBusSessionModel sessionModel = new EventBusSessionModel();
      sessionModel.setSessionCreated(false);
      EventBus.getDefault().post(sessionModel);
    }
  }
}
