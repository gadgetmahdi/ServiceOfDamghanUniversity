package serviceofdamghanuniversity.com.serviceofdamghanuniversity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import serviceofdamghanuniversity.com.serviceofdamghanuniversity.models.IMessageListener;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.webservice.WebServiceCaller;

/**
 * create with mahdi gadget & mehdi vj 11/2018
 */
public class MainActivity extends AppCompatActivity {

    WebServiceCaller webServiceCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webServiceCaller = new WebServiceCaller();
    }

    /**
     * gereftane json ha va kar bar roye an ha dar activity
     */
    public void getAlljson(){
        webServiceCaller.getAlljson(new IMessageListener() {
            @Override
            public void onResponse(List Jsonmoels) {

            }

            @Override
            public void onErrore(String error) {

            }
        });
    }


}
