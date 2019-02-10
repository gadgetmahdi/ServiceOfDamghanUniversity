package serviceofdamghanuniversity.com.serviceofdamghanuniversity.general;

import android.content.Context;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitProvider {

  private static Retrofit retrofit;

  public static Retrofit provideRetrofit(HttpUrl httpUrl, Context context) {
    if (retrofit == null) {
      return retrofit = new Retrofit.Builder()
        .baseUrl(httpUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(ClientProvider.provideOkHttpClient(context))
        .build();
    } else {
      return retrofit;
    }
  }

  public static Retrofit provideRetrofitWithInterceptor(HttpUrl httpUrl, OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
      .baseUrl(httpUrl)
      .addConverterFactory(ScalarsConverterFactory.create())
      .client(okHttpClient)
      .build();
  }

}
