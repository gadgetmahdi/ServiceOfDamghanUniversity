package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.model.listener.SaveTokenListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.module.TokenClass;


public class SplashActivity extends Activity implements SaveTokenListener {

    private TokenClass tokenClass;
    private static final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tokenClass = TokenClass.getInstance(getApplicationContext(), this);

        if (tokenClass.createNewTokenIfIsNotExist()) {
            showSplash();
        }

    }



    @Override
    public void savedToken() {
        showSplash();
    }

    @Override
    public void error() {
        tokenClass.createNewTokenIfIsNotExist();
    }


    private void showSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                openMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivityN.class);
        startActivity(intent);
        finish();
    }
}
