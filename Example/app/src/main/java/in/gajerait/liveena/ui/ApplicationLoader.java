package in.gajerait.liveena.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class ApplicationLoader extends Application {
    public static final String TAG;
    public static volatile Context applicationContext;

    public static String size;

    static {
        TAG = ApplicationLoader.class.getSimpleName();
        applicationContext = null;

    }

    private RequestQueue mRequestQueue;

    public static ApplicationLoader getInstance(Context context) {
        return (ApplicationLoader) context.getApplicationContext();
    }

    public static void check(Activity activity) {
        switch (activity.getResources().getDisplayMetrics().densityDpi) {
            case 120:
                size = "LDPI";
                System.out.println("1...LDPI");
                break;
            case 160:
                size = "MDPI";
                System.out.println("1...MDPI");
                break;
            case 240:
                size = "HDPI";
                System.out.println("1...HDPI");
                break;
            case 320:
                size = "XHDPI";
                System.out.println("1...XHDPI");
                break;
            default:
                System.out.println("1...XXHDPI");
                break;
        }
    }

    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = new RequestQueue(new DiskBasedCache(applicationContext.getCacheDir(), 10485760), new BasicNetwork(new HurlStack()));
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            this.mRequestQueue.start();
        }
        return this.mRequestQueue;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onLowMemory() {
        super.onLowMemory();
    }
}
