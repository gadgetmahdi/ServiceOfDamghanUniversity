package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.IMessageListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.Jsonmodels;

/**
 * create with mahdi gadget 11/2018
 * baraye initialize va....
 */
public class WebServiceCaller {

ApiInterface apiInterface;

public WebServiceCaller(){
    apiInterface=ApiClient.getClient().create(ApiInterface.class);
}

public void getAlljson(final IMessageListener iMessageListener){

    Call<List<Jsonmodels>> json = apiInterface.getAlljson();

    json.enqueue(new Callback<List<Jsonmodels>>() {
        @Override
        public void onResponse(Call<List<Jsonmodels>> call, Response<List<Jsonmodels>> response) {

            iMessageListener.onResponse(response.body());

        }

        @Override
        public void onFailure(Call<List<Jsonmodels>> call, Throwable t) {

            iMessageListener.onErrore(t.getMessage().toString());

        }
    });
}

}
