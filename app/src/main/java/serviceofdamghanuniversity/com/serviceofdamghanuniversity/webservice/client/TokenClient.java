package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.client;


import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.webServiceHelper.LoggingInterceptor;

/**
 * create with mahdi gadget 11/2018
 * in class singleton hast , baraye ijad object az retrofit va url va...
 */
public class TokenClient {

  //adresse asli site
  public static final String BASE_URI = "http://bit.do/";

  //sakht object az retrofit
  private static Retrofit retrofit = null;
  private static OkHttpClient.Builder client = null;


  public static OkHttpClient.Builder createOkHttpClient() {
    client = new OkHttpClient.Builder();
    client.interceptors().add(new LoggingInterceptor());

    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    client.cookieJar(new JavaNetCookieJar(cookieManager));
    return client;
  }

  public static OkHttpClient.Builder getOkHttpClient() {
    return client;
  }

  //pars karane json ha
  public static Retrofit getClient() {
    if (retrofit == null) {

      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URI)
        .client(createOkHttpClient().build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();
    }
    return retrofit;
  }

}
