package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.Jsonmodels;

/**
 * create with mahdi gadget 11/2018
 * baraye modiriyad webservice va gereftan list va model
 */
public interface ApiInterface {

//adrese file ro midim behesh
    @GET("mehdivj.php")
    Call<List<Jsonmodels>> getAlljson();

}
