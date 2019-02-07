package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.presenter;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDb;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.repository.TokenDbHelper;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.TokenContract;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.repo.TokenRepo;

public class TokenPresenterImpl implements TokenContract.TokenPresenter {

  private static final String TAG = "TokenPresenterImpl";
  private TokenRepo repo;
  private TokenContract.TokenView view;
  private OkHttpClient okHttpClient;
  private static TokenDbHelper tokenDb;

  @Inject
  public TokenPresenterImpl(TokenRepo repo, TokenContract.TokenView view , Context context, OkHttpClient okHttpClient) {
    this.repo = repo;
    this.view = view;
    this.okHttpClient = okHttpClient;
    tokenDb = new TokenDbHelper(context);
  }


  @Override
  public void getToken() {
    repo.getBusPosition(okHttpClient);
    repo.getToken().subscribe(
      token -> {
        TokenDb tokenDatabase = new TokenDb();
        tokenDatabase.setId(0);
        //tokenDatabase.setToken("eeRod37DdsGE09lNjqZGASPgjm9BlBwp");
        tokenDatabase.setToken(parseToken(token));
        try {
          tokenDb.createOrUpdate(tokenDatabase);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        view.onTokenLoaded();
      },
      throwable -> view.onTokenLoaded());
  }

  private String parseToken(String token) {
    String[] url = token.split("=");
    return url[url.length - 1];
  }

}
