package serviceofdamghanuniversity.com.serviceofdamghanuniversity.module;

import android.content.Context;
import android.widget.Toast;

import java.sql.SQLException;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.R;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.ResponseListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

public class TokenClass {

  private static TokenClass tokenClass = null;
  private static TokenDbHelper tokenDb;
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
    tokenDb = new TokenDbHelper(context);
  }


  public boolean createNewTokenIfIsNotExist() throws SQLException {
    if (tokenDb.getToken().equals("")) {
      getToken();
      return false;
    } else {
      return true;
    }
  }

  private void getToken() {
    WebServiceCaller webServiceCaller = WebServiceCaller.getInstance();

    webServiceCaller.getToken(new ResponseListener.TokenResponse() {

      @Override
      public void onResponseToken(String token) {
        try {
          TokenDb tokenDatabase = new TokenDb();
          tokenDatabase.setId(0);
          //tokenDatabase.setToken("eeRod37DdsGE09lNjqZGASPgjm9BlBwp");
          tokenDatabase.setToken(parseToken(token));
          tokenDb.createOrUpdate(tokenDatabase);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        saveTokenListener.savedToken(token);
      }

      @Override
      public void onError(String error) {
        saveTokenListener.error();
        Toast.makeText(context, R.string.server_not_fund, Toast.LENGTH_SHORT).show();

      }
    });
  }

  private String parseToken(String token) {
    String[] url = token.split("=");
    return url[url.length - 1];
  }


}
