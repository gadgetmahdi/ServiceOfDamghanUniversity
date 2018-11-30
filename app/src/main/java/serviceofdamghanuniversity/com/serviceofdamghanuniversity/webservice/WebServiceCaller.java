package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface.DevicesInterface;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.webServiceHelper.LoggingInterceptor;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.User;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.client.MainClient;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface.JsonInterface;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.client.TokenClient;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface.SessionInterface;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface.TokenInterface;

/**
 * create with mahdi gadget 11/2018
 * baraye initialize va....
 */
public class WebServiceCaller {

  private static WebServiceCaller webServiceCaller = null;

  private JsonInterface jsonInterface;
  private TokenInterface tokenInterface;
  private SessionInterface sessionInterface;
  private DevicesInterface devicesInterface;


  public static WebServiceCaller getInstance() {
    if (webServiceCaller == null)
      webServiceCaller = new WebServiceCaller();

    return webServiceCaller;
  }

  private WebServiceCaller() {
    jsonInterface = MainClient.getClient().create(JsonInterface.class);
    sessionInterface = MainClient.getClient().create(SessionInterface.class);
    devicesInterface = MainClient.getClient().create(DevicesInterface.class);
    tokenInterface = TokenClient.getClient().create(TokenInterface.class);
  }


  public void createSession(String token, final ResponseListener.Session responseSession) {
    Call<User> session = sessionInterface.createSession(token);
    session.enqueue(new Callback<User>() {
      @Override
      public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
        Log.w("mehdiVijeh", response.toString() + "");
        responseSession.onSessionCreated();
      }

      @Override
      public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        Log.w("MehdiTest11", t.toString());
        responseSession.onSessionError(t.getMessage());
      }
    });
  }


  public void getToken(final ResponseListener.TokenResponse tokenResponse) {
    Call<String> token = tokenInterface.getToken();
    token.enqueue(new Callback<String>() {
      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) {
        List<Interceptor> data = TokenClient.getOkHttpClient().interceptors();
        if (data != null && data.size() > 0) {
          for (int i = 0; i < data.size(); i++) {
            if (data.get(i) instanceof LoggingInterceptor) {
              LoggingInterceptor intercept = (LoggingInterceptor) data.get(i);
              String url = intercept.getRequestUrl();
              tokenResponse.onResponseToken(url);
              break;
            } else {
              continue;
            }
          }
        }

      }

      @Override
      public void onFailure(@NonNull Call call, @NonNull Throwable t) {
        Log.w("MehdiTest1", t.getMessage());
        tokenResponse.onError(t.getMessage());
      }


    });
  }

  public void getAllJson(String url, final ResponseListener.JsonResponse jsonResponse) {

    Call<List<Position>> json = jsonInterface.getAllJson(url);

    json.enqueue(new Callback<List<Position>>() {
      @Override
      public void onResponse(@NonNull Call<List<Position>> call, @NonNull Response<List<Position>> response) {
        Log.w("MehdiTest13", response.toString() + "");
        Log.w("MehdiTest13", response.headers() + "");
        Log.w("MehdiTest13", response.raw().headers() + "");
        jsonResponse.onResponseJson(response);

      }

      @Override
      public void onFailure(@NonNull Call<List<Position>> call, @NonNull Throwable t) {

        Log.w("MehdiTest13", t.toString() + "");
        jsonResponse.onError(t.getMessage());

      }
    });
  }

  public void getDevices(final ResponseListener.DeviceResponse deviceResponse) {

    Call<List<Devices>> json = devicesInterface.getDevices();

    json.enqueue(new Callback<List<Devices>>() {
      @Override
      public void onResponse(@NonNull Call<List<Devices>> call, @NonNull Response<List<Devices>> response) {
        Log.w("mehdiVijeh", " mvn"  + response);
        deviceResponse.onResponseDevice(response);

      }

      @Override
      public void onFailure(@NonNull Call<List<Devices>> call, @NonNull Throwable t) {

        deviceResponse.onError(t.getMessage());

      }
    });
  }

}
