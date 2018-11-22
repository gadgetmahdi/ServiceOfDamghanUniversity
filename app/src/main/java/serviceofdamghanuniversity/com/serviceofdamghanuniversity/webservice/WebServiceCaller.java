package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.IMessageListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.Jsonmodels;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.TokenModel;

/**
 * create with mahdi gadget 11/2018
 * baraye initialize va....
 */
public class WebServiceCaller {

  private static WebServiceCaller webServiceCaller = null;

  private JsonInterface jsonInterface;
  private TokenInterface tokenInterface;


  public static WebServiceCaller getInstance()
  {
    if (webServiceCaller == null)
      webServiceCaller = new WebServiceCaller();

    return webServiceCaller;
  }

  private WebServiceCaller() {
    jsonInterface = ApiClient.getClient().create(JsonInterface.class);
    tokenInterface = ApiClient.getClient().create(TokenInterface.class);
  }

  public void getToken(final IMessageListener<TokenModel> iMessageListener) {
    Call<TokenModel> json = tokenInterface.getToken();

    json.enqueue(new Callback<TokenModel>() {
      @Override
      public void onResponse(@NonNull Call<TokenModel> call, @NonNull Response<TokenModel> response) {
        iMessageListener.onResponse(response);
      }

      @Override
      public void onFailure(@NonNull Call<TokenModel> call, @NonNull Throwable t) {
        iMessageListener.onError(t.getMessage());
      }

    });
  }

  public void getAllJson(final IMessageListener<List<Jsonmodels>> iMessageListener) {

    Call<List<Jsonmodels>> json = jsonInterface.getAllJson();

    json.enqueue(new Callback<List<Jsonmodels>>() {
      @Override
      public void onResponse(@NonNull Call<List<Jsonmodels>> call, @NonNull Response<List<Jsonmodels>> response) {

        iMessageListener.onResponse(response);

      }

      @Override
      public void onFailure(@NonNull Call<List<Jsonmodels>> call, @NonNull Throwable t) {

        iMessageListener.onError(t.getMessage());

      }
    });
  }

}
