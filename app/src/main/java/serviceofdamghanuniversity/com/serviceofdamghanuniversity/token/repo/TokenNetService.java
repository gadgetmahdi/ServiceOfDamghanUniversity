package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.repo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TokenNetService {

  @GET("/greenbus2")
  Call<String> getToken();

}
