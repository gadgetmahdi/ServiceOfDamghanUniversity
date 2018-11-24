package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.rInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.jsonModel.Position;

/**
 * create with mahdi gadget 11/2018
 * baraye modiriyad webservice va gereftan list va model
 */
public interface JsonInterface {

  //address file ro midim behesh
  @Headers({"Accept: application/json"})
  @GET
  Call<List<Position>> getAllJson(
    @Url String url
  );


}
