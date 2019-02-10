package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Devices;

public interface DevicesInterface {

  @GET("api/devices")
  Call<List<Devices>> getDevices();

}
