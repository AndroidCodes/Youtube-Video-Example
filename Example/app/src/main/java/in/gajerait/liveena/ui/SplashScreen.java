package in.gajerait.liveena.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.gajerait.liveena.R;

public class SplashScreen extends Activity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = SplashScreen.this;

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(activity, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left_activity, R.anim.slide_out_right_activity);
                finish();

            }
        }, 3000);
    }
}
