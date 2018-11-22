package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create with mahdi gadget 11/2018
 * in class singleton hast , baraye ijad object az retrofit va url va...
 */
public class MainClient {

  //adresse asli site
  public static final String BASE_URI = "http://skill.du.ac.ir:8082";

  //sakht object az retrofit
  private static Retrofit retrofit = null;

  //pars karane json ha
  public static Retrofit getClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl(BASE_URI)
        .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
  }

}
