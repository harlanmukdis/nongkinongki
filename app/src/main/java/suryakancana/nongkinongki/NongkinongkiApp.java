package suryakancana.nongkinongki;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by LIMS on 05/01/2018.
 */

public class NongkinongkiApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isNetworkAvailable () {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }

}
