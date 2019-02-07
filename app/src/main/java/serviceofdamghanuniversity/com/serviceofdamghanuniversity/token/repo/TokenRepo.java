package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.repo;


import android.location.Location;
import android.util.Log;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.webServiceHelper.LoggingInterceptor;

public class TokenRepo {

  private static final String TAG = "TokenRepo";
  private TokenNetService api;
  private final PublishSubject<String> token = PublishSubject.create();


  public TokenRepo(TokenNetService api) {
    this.api = api;
  }

  public void getBusPosition(OkHttpClient okHttpClient) {

    Call<String> url = api.getToken();
    url.enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        List<Interceptor> data = okHttpClient.interceptors();
        if (data != null && data.size() > 0) {
          for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof LoggingInterceptor) {
            Log.d(TAG, "onResponse: ");
              LoggingInterceptor intercept = (LoggingInterceptor) data.get(i);
              token.onNext(intercept.getRequestUrl());
              token.onCompleted();
            }
          }
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
      }
    });
  }

  public Observable<String> getToken() {
    return token.subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());
  }

}
