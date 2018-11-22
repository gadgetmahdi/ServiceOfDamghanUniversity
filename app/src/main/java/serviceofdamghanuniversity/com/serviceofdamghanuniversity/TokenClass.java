package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Response;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.IMessageListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.TokenModel;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class TokenClass {

  private static TokenClass tokenClass = null;
  private TokenDb tokenDb;
  private Context context;

  public static void getInstance(Context context)
  {
    if (tokenClass == null)
      tokenClass = new TokenClass(context);

  }

  private TokenClass(Context context){
    this.context = context;

    tokenDb = new TokenDb(context);

    if(!tokenDb.checkIsShCreated()){
      Toast.makeText(context, "please wait until get data from server.", Toast.LENGTH_LONG).show();
      getToken();
    }
  }

  private void getToken(){
    WebServiceCaller webServiceCaller = WebServiceCaller.getInstance();
    webServiceCaller.getToken(new IMessageListener<TokenModel>() {
      @Override
      public void onResponse(Response<TokenModel> response) {
        Log.w("MehdiTest1", response.body().getUrl() + "   " + response.message());
        saveToken(response.message());
      }

      @Override
      public void onError(String error) {
        Log.w("MehdiTest", error);
        Toast.makeText(context, "server not respond, " +
          "please try again later.", Toast.LENGTH_SHORT).show();

      }
    });
  }

  private void saveToken(String token){
    tokenDb.saveToken(token);
  }

}
