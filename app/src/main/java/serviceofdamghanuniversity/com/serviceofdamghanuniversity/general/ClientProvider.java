package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general;

import android.content.Context;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.webServiceHelper.LoggingInterceptor;

public class ClientProvider {

  public static OkHttpClient provideOkHttpClient(Context context) {
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
      .connectTimeout(100, TimeUnit.SECONDS)
      .readTimeout(100, TimeUnit.SECONDS)
      .writeTimeout(100, TimeUnit.SECONDS);

    okHttpClient.addInterceptor(chain -> {
      Request.Builder requestBuilder = chain.request().newBuilder();
      Request request = requestBuilder.build();
      return chain.proceed(request);
    });

    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    okHttpClient.cookieJar(new JavaNetCookieJar(cookieManager));

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    okHttpClient.addNetworkInterceptor(loggingInterceptor);
    okHttpClient.addInterceptor(loggingInterceptor);

    return okHttpClient.build();

  }

  public static OkHttpClient provideOkHttpClientWithInterceptor() {
    OkHttpClient.Builder okHttpClient;

    okHttpClient = new OkHttpClient.Builder();
    okHttpClient.interceptors().add(new LoggingInterceptor());

    CookieManager cookieManager = new CookieManager();
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    okHttpClient.cookieJar(new JavaNetCookieJar(cookieManager));

    return okHttpClient.build();

  }

}
