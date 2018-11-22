package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SessionInterface {

  @GET("/api/session")
  Call<String> createSession(
    @Query("token") String token
  );

}
