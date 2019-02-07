package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.repo;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.model.BusPositionModel;

public interface BusPositionNetService {


  @Headers({"Accept: application/json"})
  @GET()
  Observable<BusPositionModel> getBusPosition();


}
