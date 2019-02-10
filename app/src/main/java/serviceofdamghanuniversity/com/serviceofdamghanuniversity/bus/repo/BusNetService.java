package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;

public interface BusNetService {


  @GET("api/devices")
  Observable<List<Devices>> getDevices();

}
