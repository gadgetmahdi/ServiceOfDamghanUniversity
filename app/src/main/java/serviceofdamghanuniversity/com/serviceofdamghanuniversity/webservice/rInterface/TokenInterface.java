package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.tokenWebService;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TokenInterface {

  @GET("/greenbus2")
  Call<String> getToken();

}
