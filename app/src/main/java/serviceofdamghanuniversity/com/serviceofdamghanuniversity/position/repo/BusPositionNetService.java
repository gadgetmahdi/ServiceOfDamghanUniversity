package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;

public interface BusPositionNetService {


  @Headers({"Accept: application/json"})
  @GET("/api/positions")
  Observable<List<Position>> getBusPosition();


}
