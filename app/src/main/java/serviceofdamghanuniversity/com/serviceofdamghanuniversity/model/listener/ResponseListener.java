package serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener;


import java.util.List;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;

public class ResponseListener {

  public interface JsonResponse {

    public void onResponseJson(Response<List<Position>> response);

    public void onError(String error);

  }

  public interface TokenResponse {

    public void onResponseToken(String token);

    public void onError(String error);

  }

  public interface Session {

    public void onSessionCreated();

    public void onError(String error);

  }

}
