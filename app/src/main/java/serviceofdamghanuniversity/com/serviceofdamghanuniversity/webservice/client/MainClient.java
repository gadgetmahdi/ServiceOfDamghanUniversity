package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.client;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

     /* OkHttpClient.Builder client = new OkHttpClient.Builder();
      client.addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
          Request original = chain.request();
          Request request = original.newBuilder()
            .header("Accept", "application/json")
            .method(original.method(),original.body())
            .build();
          return chain.proceed(request);
        }
      });
*/
/*

      OkHttpClient.Builder client = new OkHttpClient.Builder();

      client.addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
          Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
          return chain.proceed(request);
        }
      });
*/

      CookieManager cookieManager = new CookieManager();
      cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
      OkHttpClient.Builder okhBuilder = new OkHttpClient.Builder();
      okhBuilder.cookieJar(new JavaNetCookieJar(cookieManager));

      retrofit = new Retrofit.Builder().baseUrl(BASE_URI)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhBuilder.build())
        .build();
    }
    return retrofit;
  }

}
