package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.listener;


import retrofit2.Response;

public class ResponseListener {

  public interface JsonResponse {

    public void onResponseJson(Response response);

    public void onError(String error);

  }

  public interface TokenResponse {

    public void onResponseToken(String token);

    public void onError(String error);

  }

  public interface Session {

    public void onError(String error);

  }

}
