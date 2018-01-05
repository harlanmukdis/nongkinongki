package suryakancana.nongkinongki;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.hawk.Hawk;

import suryakancana.nongkinongki.data.remote.retrofit.RetrofitServiceFactory;
import suryakancana.nongkinongki.data.remote.retrofit.ServiceAPI;

/**
 * Created by LIMS on 05/01/2018.
 */

public class NongkinongkiApp extends Application {
    private static NongkinongkiApp mApp;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    public ServiceAPI mAPIService;

    public static NongkinongkiApp getInstance() {
        if (mApp == null) {
            mApp = new NongkinongkiApp();
        }

        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        mAPIService = RetrofitServiceFactory.createService(ServiceAPI.class);
        Hawk.init(getApplicationContext()).build();
    }

    public boolean isNetworkAvailable () {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }

}
