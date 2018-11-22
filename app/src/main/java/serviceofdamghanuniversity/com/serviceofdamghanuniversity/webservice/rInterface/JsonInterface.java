package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.jsonWebService;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.jsonModel.Jsonmodels;

/**
 * create with mahdi gadget 11/2018
 * baraye modiriyad webservice va gereftan list va model
 */
public interface JsonInterface {

//address file ro midim behesh
    @GET("http://31.7.90.52:8082/?token=ty2LedFSj6ZuClSH378PFREJ1G3ApB90")
    Call<List<Jsonmodels>> getAllJson();



}
