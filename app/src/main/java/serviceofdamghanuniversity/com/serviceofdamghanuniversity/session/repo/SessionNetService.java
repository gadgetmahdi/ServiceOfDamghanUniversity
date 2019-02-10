package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.repo;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.User;

public interface SessionNetService {

  @GET("/api/session")
  Observable<User> createSession(
    @Query("token") String token
  );

}
