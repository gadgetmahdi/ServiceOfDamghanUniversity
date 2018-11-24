package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.User;

public interface SessionInterface {

  @GET("/api/session")
  Call<User> createSession(
    @Query("token") String token
  );

}
