package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onesignal.OneSignal;

public class ApplicationActivity extends Application {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true).init();
    }
}
