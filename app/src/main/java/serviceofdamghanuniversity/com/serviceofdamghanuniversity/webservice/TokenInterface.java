package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.TokenModel;

public interface TokenInterface {

  @GET("bit.do/greenbus2")
  Call<TokenModel> getToken();

}
