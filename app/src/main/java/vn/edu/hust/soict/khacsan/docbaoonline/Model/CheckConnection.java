package vn.edu.hust.soict.khacsan.docbaoonline.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by San on 11/18/2017.
 */

public class CheckConnection {
    public static boolean internetConnectionCheck(Context context) {
        if(context == null) return false;
        Boolean haveConnectedWifi = false;
        Boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = cm.getAllNetworkInfo();

        for (NetworkInfo in : info) {
            if (in.getTypeName().equalsIgnoreCase("WIFI")) {
                if (in.isConnected()) haveConnectedWifi = true;
            }
            if (in.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (in.isConnected()) haveConnectedMobile = true;
            }
        }
        return haveConnectedMobile | haveConnectedWifi;
    }
}
