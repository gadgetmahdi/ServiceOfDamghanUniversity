package serviceofdamghanuniversity.com.serviceofdamghanuniversity.models;

import java.util.List;

/**
 * create with mahdi gadget 11/2018
 * listener baraye webservice
 */
public interface IMessageListener<T> {

    public void onResponse(List<T> Jsonmoels);

    public void onErrore(String error);

}
