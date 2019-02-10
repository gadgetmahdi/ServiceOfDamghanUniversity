package serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.webServiceHelper;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

  private String requestUrl;

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    Request request = chain.request();
    Response response = chain.proceed(request);
    setRequestUrl(response.request().url().toString());
    return response;

  }
}