package in.gajerait.liveena.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils {
    private Context _context;

    public JsonUtils(Context context) {
        this._context = context;
    }

    public static String getJSONString(String url) {
        String jsonString = null;
        HttpURLConnection linkConnection = null;
        try {
            linkConnection = (HttpURLConnection) new URL(url).openConnection();
            if (linkConnection.getResponseCode() == 200) {
                InputStream linkinStream = linkConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int j = linkinStream.read();
                    if (j == -1) {
                        break;
                    }
                    baos.write(j);
                }
                jsonString = new String(baos.toByteArray());
            }
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        } catch (Throwable th) {
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        }
        return jsonString;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info == null) {
            return false;
        }
        for (NetworkInfo state : info) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public int getScreenWidth() {
        Display display = ((WindowManager) this._context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();
        return point.x;
    }
}
