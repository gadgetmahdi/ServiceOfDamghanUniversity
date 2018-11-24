package serviceofdamghanuniversity.com.serviceofdamghanuniversity.module;

import android.content.Context;
import android.widget.Toast;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class TokenClass {

  private static TokenClass tokenClass = null;
  private static TokenDb tokenDb;
  private Context context;
  private SaveTokenListener saveTokenListener;


  public static TokenClass getInstance(Context context, SaveTokenListener saveTokenListener) {
    if (tokenClass == null)
      tokenClass = new TokenClass(context, saveTokenListener);

    return tokenClass;
  }

  private TokenClass(Context context, SaveTokenListener saveTokenListener) {
    this.context = context;
    this.saveTokenListener = saveTokenListener;
    tokenDb = new TokenDb(context);
  }


  public boolean createNewTokenIfIsNotExist() {
    if (!tokenDb.checkIsShCreated()) {
      getToken();
      return false;
    }
    return true;
  }

  private void getToken() {
    WebServiceCaller webServiceCaller = WebServiceCaller.getInstance();

    webServiceCaller.getToken(new ResponseListener.TokenResponse() {

      @Override
      public void onResponseToken(String token) {
        saveToken(parseToken(token));
        saveTokenListener.savedToken();
      }

      @Override
      public void onError(String error) {
        saveTokenListener.error();
        Toast.makeText(context, "server not respond, " +
          "please try again later.", Toast.LENGTH_SHORT).show();

      }
    });
  }

  private String parseToken(String token) {
    String[] url = token.split("=");
    return url[url.length - 1];
  }

  private void saveToken(String token) {
    tokenDb.saveToken(token);
  }


}
