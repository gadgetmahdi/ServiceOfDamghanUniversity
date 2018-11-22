package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models;


import retrofit2.Response;

/**
 * create with mahdi gadget 11/2018
 * listener baraye webservice
 */
public interface IMessageListener<T> {

    public void onResponse(Response<T> response);

    public void onError(String error);

}
