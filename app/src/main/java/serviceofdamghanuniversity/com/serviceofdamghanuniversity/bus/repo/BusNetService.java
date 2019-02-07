package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.repo;


import retrofit2.http.GET;
import rx.Observable;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;

public interface BusNetService {


  @GET("api/devices")
  Observable<Devices> getDevices();

}
