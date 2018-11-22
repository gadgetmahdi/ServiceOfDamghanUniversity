package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.content.Context;
import android.widget.Toast;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class TokenClass implements ResponseListener.Session {

  private static TokenClass tokenClass = null;
  private static TokenDb tokenDb;
  private Context context;
  private WebServiceCaller webServiceCaller;

  public static TokenClass getInstance(Context context) {
    if (tokenClass == null)
      tokenClass = new TokenClass(context);

    return tokenClass;
  }

  private TokenClass(Context context) {
    this.context = context;
    tokenDb = new TokenDb(context);
    webServiceCaller = WebServiceCaller.getInstance(context);
  }

  public void generateNewToken() {
    getToken();
  }

  public void createNewTokenIfIsNotExist() {
    if (!tokenDb.checkIsShCreated()) {
      Toast.makeText(context, "please wait until get data from server.", Toast.LENGTH_LONG).show();
      getToken();
    } else {
      webServiceCaller.createSession(this);
    }
  }

  private void getToken() {
    webServiceCaller.getToken(new ResponseListener.TokenResponse() {

      @Override
      public void onResponseToken(String token) {
        saveToken(parseToken(token));

        // create session after get and save token
        webServiceCaller.createSession(TokenClass.this);
      }

      @Override
      public void onError(String error) {
        Toast.makeText(context, "server not respond, " +
          "please try again later.", Toast.LENGTH_SHORT).show();

      }
    });
  }

  private String parseToken(String token) {
    String[] url = token.split("/");
    return url[url.length - 1];
  }

  private void saveToken(String token) {
    tokenDb.saveToken(token);
  }


  //when cant create new session
  @Override
  public void onError(String error) {
    Toast.makeText(context, "server not respond, " +
      "please try again later.", Toast.LENGTH_SHORT).show();

  }
}
